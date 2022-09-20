package compedia.vn.tickmi.mail.task.ticket;

import compedia.vn.tickmi.mail.entity.EventRequest;
import compedia.vn.tickmi.mail.entity.EventRequestDetail;
import compedia.vn.tickmi.mail.entity.MailRequest;
import compedia.vn.tickmi.mail.entity.Ticket;
import compedia.vn.tickmi.mail.repository.ProviderRepository;
import compedia.vn.tickmi.mail.service.EventRequestDetailService;
import compedia.vn.tickmi.mail.service.EventRequestService;
import compedia.vn.tickmi.mail.service.MailRequestService;
import compedia.vn.tickmi.mail.service.TicketService;
import compedia.vn.tickmi.mail.task.qr.GenerateQR;
import compedia.vn.tickmi.mail.utils.DbConstant;
import compedia.vn.tickmi.mail.utils.GenerateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;

@Component
@EnableScheduling
@EnableAsync
@Log4j2
public class HandleTicketService {

    @Autowired
    TicketService ticketService;

    @Autowired
    MailRequestService mailRequestService;

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    EventRequestService eventRequestService;

    @Autowired
    EventRequestDetailService eventRequestDetailService;

    private static final Queue<EventRequest> queueEventRequest = new ArrayDeque<>();
    private static final Queue<EventRequestDetail> queueEventRequestDetails = new ArrayDeque<>();

    /**
     * Method to get data from DB EVENT_REQUEST -> push queue to handle process other
     */
    @Async
    @Scheduled(fixedRate = 3000)
    public void getEventRequestsLoop() {
        try {
            log.info("EVENT_REQUEST =>>>>Start to get event request with limit");
            List<EventRequest> eventRequestList = eventRequestService.getEventRequestList();
            if (!CollectionUtils.isEmpty(eventRequestList)) {
                // Update n object
                eventRequestService.updateEventRequestByStatus(eventRequestList, DbConstant.STATUS_EVENT_REQUEST);
                // Push n object to queue
                queueEventRequest.addAll(eventRequestList);
                log.info("EVENT_REQUEST =>>>> Push event request list success!");
            } else {
                log.info("EVENT_REQUEST =>>>> Data Event request is empty!");
            }
        } catch (Exception e) {
            log.error("Error to get event request", e);
        }
    }


    /**
     * Method to handle from queue -> Set value -> Insert value to db EVENT_REQUEST_DETAIL
     */
    @Async
    @Scheduled(fixedRate = 1000)
    public void insertCacheEventRequestDetail() {
        log.info("INSERT EVENT REQUEST DETAIL DB =>>> Get data from queue event request to create event request detail");
        if (!queueEventRequest.isEmpty()) {
            EventRequest eventRequest = queueEventRequest.poll();
            List<EventRequestDetail> details = new ArrayList<>();
            try {
                for (int i = 1; i <= eventRequest.getQuantity(); i++) {
                    EventRequestDetail dto = new EventRequestDetail();
                    dto.setIndexTicket(i);
                    dto.setCodeTicket(GenerateUtils.generateCodeTicket());
                    dto.setStatus(DbConstant.STATUS_NEW_EVENT_REQUEST_DETAIL);
                    dto.setRetry(DbConstant.INIT_RETRY);
                    dto.setEventId(eventRequest.getEventId());
                    dto.setTicketEventId(eventRequest.getTicketEventId());
                    dto.setProviderId(eventRequest.getProviderId());
                    dto.setObjectId(eventRequest.getObjectId());
                    dto.setType(eventRequest.getType());
                    dto.setEventRequestId(eventRequest.getId());
                    dto.setNameGuest(eventRequest.getNameGuest());
                    dto.setPhoneGuest(eventRequest.getPhoneGuest());
                    dto.setEmailGuest(eventRequest.getEmailGuest());
                    details.add(dto);
                }
                eventRequestDetailService.saveEventRequestDetails(details);
                log.info("INSERT EVENT REQUEST DETAIL DB =>>>  Push event request detail success!");
            } catch (Exception e) {
                log.error("Error to insert cache event request detail", e);
            }
        } else {
            log.info("INSERT EVENT REQUEST DETAIL DB =>>> DATA EMPTY!");
        }
    }


    /***
     * Method to get event request detail -> set value -> push queue to handle process other
     */
    @Async
    @Scheduled(fixedRate = 5000)
    public void getEventRequestDetailLoop() {
        try {
            log.info("GET EVENT REQUEST DETAIL =>>>> Get all event request detail limit");
            // Get n object
            List<EventRequestDetail> eventRequestDetails = eventRequestDetailService.getAllEventRequestDetailLimit();
            // update object
            if (!CollectionUtils.isEmpty(eventRequestDetails)) {
                // create event request details
                eventRequestDetails.stream().forEach(x -> x.setStatus(DbConstant.STATUS_EVENT_REQUEST_DETAIL));
                eventRequestDetailService.saveEventRequestDetails(eventRequestDetails);
                // Insert queue
                queueEventRequestDetails.addAll(eventRequestDetails);
            } else {
                log.info("GET EVENT REQUEST DETAIL =>>>> Data Event request detail is empty!");
            }
        } catch (Exception e) {
            log.error("Error to get event request detail", e);
        }
    }


    /**
     * Handle to get data from event_request_detail -> process -> generate path QR
     */
    @Async
    @Scheduled(fixedRate = 1000)
    public void generateQRPathImage() {
        log.info("GENERATE PATH QR ==>>>>>>> START");
        if (!queueEventRequestDetails.isEmpty()) {
            EventRequestDetail detail = queueEventRequestDetails.poll();
            String pathQr = null;
            for (int i = 0; i < DbConstant.MAX_RETRY; i++) {
                // Retry < 3
                if (detail.getRetry() < DbConstant.MAX_RETRY) {
                    try {
                        pathQr = GenerateQR.handlerGeneratePathQR(detail.getCodeTicket(), detail.getEventId(), detail.getTicketEventId(),
                                detail.getObjectId(), detail.getType(), detail.getIndexTicket());
                        // Success
                        // Remove in event detail
                        eventRequestDetailService.deleteEventRequestDetail(detail);
                        log.info("Delete event request detail");
                        // Insert to ticket
                        Ticket ticket = createTicket(detail, pathQr, DbConstant.TICKET_NOT_CHECKIN);
                        ticketService.saveTicket(ticket);
                        // Update ticket generic
                        Optional<EventRequest> eventRequest = eventRequestService.
                                findEventRequestByIdAndStatus(detail.getEventRequestId(), DbConstant.STATUS_EVENT_REQUEST);

                        if (eventRequest.isPresent()) {
                            int quantityGen = eventRequest.get().getTicketGeneration() + 1;
                            eventRequest.get().setTicketGeneration(quantityGen);
                            if (eventRequest.get().getQuantity().intValue() == eventRequest.get().getTicketGeneration().intValue()) {
                                // Insert to email
                                mailRequestService.saveMailRoot(createMailRequest(eventRequest.get()));
                                // Delete event request
                                eventRequestService.deleteEventRequest(eventRequest.get());
                            }
                            eventRequestService.updateEventRequest(eventRequest.get());
                        } else {
                            log.error("Don't exit event request");
                        }
                        log.info("Ticket save ticket");
                        break;
                    } catch (Exception e) {
                        // False
                        int retryBefore = detail.getRetry() + 1;
                        detail.setRetry(retryBefore);
                        log.error(e.getMessage(), e);
                    }
                } else {
                    // Delete in ticket request detail
                    eventRequestDetailService.deleteEventRequestDetail(detail);
                    // Insert into ticket
                    ticketService.saveTicket(createTicket(detail, pathQr, DbConstant.TICKET_FALSE));
                }
                log.info("GENERATE PATH QR ==>>>>>>> END");
            }
        } else {
            log.debug("GENERATE PATH QR ==>>>>>>> Queue event request detail is empty!");
        }
    }

    private Ticket createTicket(EventRequestDetail detail, String pathQR, Integer status) {
        Ticket ticket = new Ticket();
        ticket.setProviderId(detail.getProviderId());
        ticket.setTicketEventId(detail.getTicketEventId());
        ticket.setEventId(detail.getEventId());
        ticket.setPathQr(pathQR);
        Date now = new Date();
        ticket.setTimeGenerate(new Timestamp(now.getTime()));
        ticket.setModifiedTime(new Timestamp(now.getTime()));
        ticket.setIndexQr(detail.getIndexTicket());
        ticket.setTicketCode(detail.getCodeTicket());
        ticket.setStatus(status);
        ticket.setObjectId(detail.getObjectId());
        ticket.setType(detail.getType());
        return ticket;
    }

    private MailRequest createMailRequest(EventRequest eventRequest) {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setObjectId(eventRequest.getObjectId());
        mailRequest.setStatus(DbConstant.MAIL_ROOT_STATUS_NEW);
        mailRequest.setRetry(DbConstant.INIT_RETRY);
        Date now = new Date();
        mailRequest.setCreateTime(new Timestamp(now.getTime()));
        mailRequest.setModifiedTime(new Timestamp(now.getTime()));
        mailRequest.setProviderId(eventRequest.getProviderId());
        mailRequest.setType(eventRequest.getType());
        mailRequest.setEventId(eventRequest.getEventId());
        mailRequest.setTicketEventId(eventRequest.getTicketEventId());
        mailRequest.setNameGuest(eventRequest.getNameGuest());
        mailRequest.setPhoneGuest(eventRequest.getPhoneGuest());
        mailRequest.setEmailGuest(eventRequest.getEmailGuest());
        return mailRequest;
    }
}
