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
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;

@Component
@EnableScheduling
@Log4j2
@EnableAsync
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
    @Synchronized
    @Scheduled(fixedRate = 5000)
    public void getEventRequestsLoop() {
        try {
            List<EventRequest> eventRequestList = eventRequestService.getEventRequestList();
            if (!CollectionUtils.isEmpty(eventRequestList)) {
                // Update n object
                eventRequestService.updateEventRequestByStatus(eventRequestList, DbConstant.STATUS_EVENT_REQUEST);
                // Push n object to queue
                queueEventRequest.addAll(eventRequestList);
                log.info("EVENT_REQUEST =>>>> Push event request list success!");
            }
        } catch (Exception e) {
            log.error("Error to get event request", e);
        }
    }


    /**
     * Method to handle from queue -> Set value -> Insert value to db EVENT_REQUEST_DETAIL
     */
    @Synchronized
    @Scheduled(fixedRate = 2000)
    public void insertCacheEventRequestDetail() {
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
        }
    }


    /***
     * Method to get event request detail -> set value -> push queue to handle process other
     */
    @Synchronized
    @Scheduled(fixedRate = 5000)
    public void getEventRequestDetailLoop() {
        try {
            // Get n object
            List<EventRequestDetail> eventRequestDetails = eventRequestDetailService.getAllEventRequestDetailLimit();
            // update object
            if (!CollectionUtils.isEmpty(eventRequestDetails)) {
                // create event request details
                eventRequestDetails.stream().forEach(x -> x.setStatus(DbConstant.STATUS_EVENT_REQUEST_DETAIL));
                eventRequestDetailService.saveEventRequestDetails(eventRequestDetails);
                // Insert queue
                queueEventRequestDetails.addAll(eventRequestDetails);
                log.info("Save to queue event request detail success!");
            }
        } catch (Exception e) {
            log.error("Error to get event request detail", e);
        }
    }


    /**
     * Handle to get data from event_request_detail -> process -> generate path QR
     */
    @Synchronized
    @Scheduled(fixedRate = 1000)
    public void generateQRPathImage() throws InterruptedException {
        if (!queueEventRequestDetails.isEmpty()) {
            EventRequestDetail detail = queueEventRequestDetails.poll();
            detail.setRetry(0);

            // Update ticket generic
            EventRequest eventRequest = eventRequestService.findEventRequestById(detail.getEventRequestId()).orElse(null);
            if (null == eventRequest) {
                return;
            }

            int quantity = eventRequest.getQuantity();
            Integer eventRequestId = eventRequest.getId();
            log.info("Event request id:" + eventRequestId);

            String pathQr = null;
            String nameTicket = "EV_" + detail.getObjectId() + detail.getType() + detail.getIndexTicket();

            while (detail.getRetry().intValue() < DbConstant.MAX_RETRY) {
                try {
                    pathQr = GenerateQR.handlerGeneratePathQR(detail.getCodeTicket(), detail.getEventId(), detail.getTicketEventId(),
                            detail.getObjectId(), detail.getType(), detail.getIndexTicket(), nameTicket);

                    // Success and remove in event detail
                    eventRequestDetailService.deleteEventRequestDetail(detail);

                    // Insert to ticket
                    Ticket ticket = createTicket(detail, pathQr, DbConstant.TICKET_NOT_CHECKIN, nameTicket);
                    log.info("Save ticket service success!" + ticket.toString());
                    ticketService.saveTicket(ticket);

                    log.info("Create ticket and increase amount!");
                    int quantityGen = eventRequest.getTicketGeneration() + 1;
                    eventRequest.setTicketGeneration(quantityGen);

                    if (quantity == quantityGen) {
                        log.info("Save mail request success!");
                        mailRequestService.saveMailRoot(createMailRequest(eventRequest));

                        log.info("Delete event request success id: " + eventRequestId);
                        eventRequestService.deleteEventRequestById(eventRequestId);
                        log.info("Quantity: " + eventRequest.getQuantity() + " - " + eventRequest.getTicketGeneration());
                    } else {
                        log.info("Quantity: " + eventRequest.getQuantity() + " - " + eventRequest.getTicketGeneration());
                        eventRequestService.updateEventRequest(eventRequest);
                    }
                    break;
                } catch (Exception e) {
                    int retryBefore = detail.getRetry() + 1;
                    detail.setRetry(retryBefore);
                    log.error(e.getMessage(), e);
                }
            }

            if (detail.getRetry().intValue() == DbConstant.MAX_RETRY) {
                // Delete in ticket request detail
                eventRequestDetailService.deleteEventRequestDetail(detail);

                // Delete event request
                eventRequestService.deleteEventRequestById(eventRequestId);

                // Insert into ticket
                ticketService.saveTicket(createTicket(detail, pathQr, DbConstant.TICKET_FALSE, nameTicket));
            }
            Thread.sleep(300);
        }
    }

    private Ticket createTicket(EventRequestDetail detail, String pathQR, Integer status, String nameTicket) {
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
        ticket.setNameGuest(detail.getNameGuest());
        ticket.setPhoneGuest(detail.getPhoneGuest());
        ticket.setEmailGuest(detail.getEmailGuest());
        ticket.setNameTicket(nameTicket);
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
        mailRequest.setQuantity(eventRequest.getQuantity());
        return mailRequest;
    }
}
