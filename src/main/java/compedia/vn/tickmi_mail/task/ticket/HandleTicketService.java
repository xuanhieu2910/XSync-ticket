package compedia.vn.tickmi_mail.task.ticket;

import compedia.vn.tickmi_mail.entity.*;
import compedia.vn.tickmi_mail.service.*;
import compedia.vn.tickmi_mail.task.mail.HandleMailService;
import compedia.vn.tickmi_mail.task.qr.GenerateQR;
import compedia.vn.tickmi_mail.utils.DbConstant;
import compedia.vn.tickmi_mail.utils.GenerateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class HandleTicketService {

    private final static Logger logger = LoggerFactory.getLogger(HandleMailService.class);

    private static final Queue<EventRequestDetail> queueEventRequestDetails = new ArrayDeque<>();
    private static final Queue<EventRequest> queueEventRequest = new ArrayDeque<>();

    private static final Queue<List<EventRequestDetail>>queueEventDetailsToMai = new ArrayDeque<>();

    @Autowired
    EventRequestService eventRequestService;

    @Autowired
    EventRequestDetailService eventRequestDetailService;

    @Autowired
    TicketService ticketService;

    @Autowired
    MailRootService mailRootService;

    /**
     *
     *  Method to get data from DB EVENT_REQUEST -> push queue to handle process other
     *
     *
     * */
    @Async
    @Scheduled(fixedRate = 1000)
    public void getEventRequestsLoop() {
        try {
            List<EventRequest> eventRequestList = eventRequestService.getEventRequestList(
                    DbConstant.STATUS_NEW_EVENT_REQUEST, DbConstant.SIZE_LIMIT);
            if (!CollectionUtils.isEmpty(eventRequestList)) {
                // Update n object
                eventRequestService.updateEventRequestByStatus(eventRequestList, DbConstant.STATUS_EVENT_REQUEST);
                // Push n object to queue
                queueEventRequest.addAll(eventRequestList);
            } else {
                logger.info("Data Event request is empty!");
            }
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }






    /**
     *
     * Method to handle from queue -> Set value -> Insert value to db EVENT_REQUEST_DETAIL
     *
     * */
    @Async
    @Scheduled(fixedRate = 500)
    public void insertCacheEventRequestDetail() {
        if (!queueEventRequest.isEmpty()) {
            EventRequest eventRequest = queueEventRequest.poll();
            List<EventRequestDetail> details = new ArrayList<>();
            try {
                for (int i = 1; i <= eventRequest.getQuantity(); i++) {
                    EventRequestDetail dto = new EventRequestDetail();
                    dto.setIndexTicket(i);
                    dto.setCodeTicket(GenerateUtils.generateCodeTicket());
                    dto.setStatus(DbConstant.STATUS_NEW_EVENT_REQUEST);
                    dto.setRetry(DbConstant.INIT_RETRY);
                    dto.setEventRequestId(eventRequest.getId());
                    dto.setEventId(eventRequest.getEventId());
                    dto.setTicketEventId(eventRequest.getTicketEventId());
                    dto.setGuestId(eventRequest.getGuestId());
                    dto.setProviderId(eventRequest.getProviderId());
                    dto.setUserId(eventRequest.getUserId());
                    dto.setGuestCode(eventRequest.getGuestCode());
                    details.add(dto);
                }
                eventRequestDetailService.saveEventRequestDetails(details);
            } catch (Exception e) {
                logger.error("Lỗi nè:" + e.getMessage());
            }
        }
    }






    /***
     *
     * Method to get event request detail -> set value -> push queue to handle process other
     *
     */
    @Async
    @Scheduled(fixedRate = 1000)
    public void getEventRequestDetailLoop () {
        try {
            // Get n object
            List<EventRequestDetail> eventRequestDetails = eventRequestDetailService.
                    getAllEventRequestDetailLimit(DbConstant.SIZE_LIMIT, DbConstant.STATUS_NEW_EVENT_REQUEST_DETAIL,
                            DbConstant.STATUS_EVENT_REQUEST_DETAIL, DbConstant.MAX_RETRY_DETAIL);
            // update object
            if (!CollectionUtils.isEmpty(eventRequestDetails)) {
                eventRequestDetails.stream().forEach(x -> x.setStatus(DbConstant.STATUS_EVENT_REQUEST_DETAIL_FLAT));
                eventRequestDetailService.saveEventRequestDetails(eventRequestDetails);
                // Insert queue
                queueEventRequestDetails.addAll(eventRequestDetails);
            }
            else {
                logger.info("Data Event request detail is empty!");
            }
        }catch (Exception e) {
            logger.error("Lỗi nè =======>>>>> " + e.getMessage());
        }
    }




    /**
     *  Handle to get data from event_request_detail -> process -> generate path QR
     *
     * */
    @Async
    @Scheduled(fixedRate = 500)
    public void generateQRPathImage () {
        logger.info("===================================== START HANDLE GEN QR =============================================");
            //Get n object
            //Create path image
            //Success : Update  Path + flat -> Update event request
            //False : Update flat
        if (!queueEventRequestDetails.isEmpty()) {
            EventRequestDetail detail = queueEventRequestDetails.poll();
            String pathQr = GenerateQR.handlerGeneratePathQR(detail.getEventId(),detail.getTicketEventId(),detail.getGuestId(),detail.getIndexTicket());
            boolean checkEventRequest = true;
            try {
                logger.info("================================== START HANDLE JOB 1 =================================================");
                GenerateQR.handleImageGenerateQR(detail.getCodeTicket(),pathQr,GenerateUtils.genNameTicket(detail.getGuestId().longValue(),detail.getIndexTicket()));
                // Success
                // Update status
                detail.setStatus(DbConstant.STATUS_EVENT_REQUEST_DETAIL_DONE);
                detail.setPathImage(pathQr);
                Date now = new Date();
                detail.setTimeGenerate(new Timestamp(now.getTime()));
                detail.setModifiedTime(new Timestamp(now.getTime()));
                eventRequestDetailService.saveEventRequestDetail(detail);
                // Update event root
                Optional<EventRequest> eventRequest = eventRequestService.
                        findEventRequestByIdAndStatus(detail.getEventRequestId(),DbConstant.STATUS_EVENT_REQUEST);
                /**
                 * Nếu tồn tại event request -> Update
                 * Không tồn tại -> xóa ở bảng detail (loại bỏ dư thừa dữ liệu)
                 *
                 * */
                if (eventRequest.isPresent()) {
                    int quantityGen = eventRequest.get().getQuantityGen() + 1;
                    eventRequest.get().setQuantityGen(quantityGen);
                    // update status or quantity gen event request
                    if (eventRequest.get().getQuantity().intValue() == eventRequest.get().getQuantityGen().intValue()) {
                        List<EventRequestDetail> requestDetails = eventRequestDetailService.requestDetails(eventRequest.get().getId());
                        if (!CollectionUtils.isEmpty(requestDetails)){
                            try {
                                queueEventDetailsToMai.add(requestDetails);
                            }catch (Exception e) {
                                logger.error("Lỗi này ==============>>>> " + e.getMessage());
                            }
                        }
                    }
                    eventRequestService.updateEventRequest(eventRequest.get());
                }
                else {
                    eventRequestDetailService.deleteEventRequestDetail(detail);
                }
                logger.info("================================== END HANDLE JOB 1 =================================================");
            }catch (Exception e) {
                // False
                // update retry + status
                detail.setStatus(DbConstant.STATUS_EVENT_REQUEST_DETAIL);
                if (detail.getRetry().equals(DbConstant.MAX_RETRY_DETAIL)) {
                    // delete event request detail
                    eventRequestDetailService.deleteEventRequestDetail(detail);
                    // update event request
                    Optional<EventRequest> eventRequest = eventRequestService.
                            findEventRequestByIdAndStatus(detail.getEventRequestId(),DbConstant.STATUS_EVENT_REQUEST);
                    /**
                     * Nếu tồn tại event request -> Update
                     * Không tồn tại -> xóa ở bảng detail (loại bỏ dư thừa dữ liệu)
                     *
                     * */
                    if (eventRequest.isPresent()) {
                        eventRequest.get().setStatus(DbConstant.STATUS_FALSE);
                        eventRequestService.updateEventRequest(eventRequest.get());
                    }
                    else {
                        eventRequestDetailService.deleteEventRequestDetail(detail);
                    }
                }
                else {
                    detail.setRetry(detail.getRetry().intValue() + 1);
                    Date now = new Date();
                    detail.setTimeGenerate(new Timestamp(now.getTime()));
                }
                eventRequestDetailService.saveEventRequestDetail(detail);
            }
        }
        else {
            logger.debug("Queue event request detail is empty!");
        }
        logger.info("======================================= END HANDLE PROCESS HANDLE QR =======================================");
    }



    @Async
    @Scheduled(fixedRate = 1000)
    public void insertToEventMail () {
        try {
            if (!queueEventDetailsToMai.isEmpty()) {
                // Get event request detail
                List<EventRequestDetail> eventRequestDetails = queueEventDetailsToMai.poll();
                // send to ticket
                ticketService.saveAllTickets(createTicket(eventRequestDetails));
                // update eventRequest
                eventRequestService.updateEventRequestByIdAndStatus(eventRequestDetails.get(0).getEventRequestId(),DbConstant.STATUS_READY_SEND);
                // create mail root
                mailRootService.saveMailRoot(createMailRoot(eventRequestDetails.get(0)));
                // remove event detail
                eventRequestDetailService.deleteEventRequestDetails(eventRequestDetails);
                logger.info("-----------------------MAIL SUCCESS------------------------");
            }
            else {
                logger.info("Queue Mail is empty!");
            }
        }catch (Exception e){
            logger.error("Lỗi insert to event mail ===>>> " + e.getMessage());
        }
    }



    private List<Ticket> createTicket (List<EventRequestDetail> eventRequestDetails) {
        List<Ticket> tickets = new ArrayList<>();
        for (EventRequestDetail dto: eventRequestDetails) {
            Ticket ticket = new Ticket();
            ticket.setTicketEventId(dto.getTicketEventId());
            ticket.setEventId(dto.getEventId());
            ticket.setGuestId(dto.getGuestId());
            ticket.setPathQr(dto.getPathImage());
            ticket.setTIME_GENERATE(dto.getTimeGenerate());
            ticket.setIndexQr(dto.getIndexTicket());
            ticket.setTicketCode(dto.getCodeTicket());
            ticket.setGuestCode(dto.getGuestCode());
            ticket.setUserId(dto.getUserId());
            ticket.setGuestId(dto.getGuestId());
            ticket.setStatus(DbConstant.TICKET_NOT_CHECKIN);
            tickets.add(ticket);
        }
        return tickets;
    }

    private MailRoot createMailRoot (EventRequestDetail eventRequestDetail) {
        MailRoot mailRoot = new MailRoot();
        mailRoot.setGuestId(eventRequestDetail.getGuestId());
        mailRoot.setStatus(DbConstant.MAIL_ROOT_NEW);
        mailRoot.setRetry(DbConstant.INIT_RETRY);
        Date now = new Date();
        mailRoot.setCreateTime(new Timestamp(now.getTime()));
        mailRoot.setModifiedTime(new Timestamp(now.getTime()));
        mailRoot.setProviderId(eventRequestDetail.getProviderId());
        return mailRoot;
    }
}
