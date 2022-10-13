package compedia.vn.tickmi.mail.task;

import com.antkorwin.xsync.XSync;
import compedia.vn.tickmi.mail.entity.EventRequest;
import compedia.vn.tickmi.mail.entity.EventRequestDetail;
import compedia.vn.tickmi.mail.entity.MailRequest;
import compedia.vn.tickmi.mail.entity.Ticket;
import compedia.vn.tickmi.mail.service.EventRequestDetailService;
import compedia.vn.tickmi.mail.service.EventRequestService;
import compedia.vn.tickmi.mail.service.MailRequestService;
import compedia.vn.tickmi.mail.service.TicketService;
import compedia.vn.tickmi.mail.task.qr.GenerateQR;
import compedia.vn.tickmi.mail.utils.DbConstant;
import lombok.extern.log4j.Log4j2;


import java.sql.Timestamp;
import java.util.Date;

@Log4j2
public class GenerateQREventRequestDetail implements Runnable{


    private EventRequestDetail detail;
    private EventRequestService eventRequestService;
    private EventRequestDetailService eventRequestDetailService;
    private MailRequestService mailRequestService;
    private TicketService ticketService;
    private XSync<Long> xSync;
    public GenerateQREventRequestDetail(EventRequestDetail detail, EventRequestService eventRequestService,
                                         EventRequestDetailService eventRequestDetailService, MailRequestService mailRequestService,
                                         TicketService ticketService,
                                         XSync<Long> xSync) {
        this.detail = detail;
        this.eventRequestService = eventRequestService;
        this.eventRequestDetailService = eventRequestDetailService;
        this.mailRequestService = mailRequestService;
        this.ticketService = ticketService;
        this.xSync = xSync;
    }

    @Override
    public void run() {
        Long eventRequestDetailId = detail.getId();
        log.info(" Event request detail id : " + eventRequestDetailId);
        String pathQr = null;
        String nameTicket = "EV_" + detail.getObjectId() + detail.getType() + detail.getIndexTicket();

        try {
            pathQr = GenerateQR.handlerGeneratePathQR(detail.getCodeTicket(), detail.getEventId(), nameTicket);
            // Insert to ticket
            Ticket ticket = createTicket(detail, pathQr, DbConstant.TICKET_NOT_CHECKIN, nameTicket);
            ticketService.saveTicket(ticket);
            log.info("Save ticket service success id {}",ticket.getTicketId());
            log.info("Create ticket and increase amount!");

            xSync.execute(detail.getEventRequestId(), () -> {
                eventRequestService.updateEventRequestByIdEventRequestDetail(detail.getEventRequestId());

                EventRequest eventRequest = eventRequestService.findEventRequestById(detail.getEventRequestId()).orElse(null);
                if (null == eventRequest) {
                    return;
                }

                log.info("Update event request quantity gen : {}", eventRequest.getTicketGeneration());
                if (eventRequest.getQuantity().equals(eventRequest.getTicketGeneration())) {
                    log.info("Quantity: " + eventRequest.getQuantity() + " - " + eventRequest.getTicketGeneration());
                    mailRequestService.saveMailRoot(createMailRequest(eventRequest));
                    log.info("Save mail request success!");
                    log.info("Delete event request success id: " + detail.getEventRequestId());
                    eventRequestService.deleteEventRequestById(detail.getEventRequestId());
                    log.info("Delete event request success id: " + detail.getEventRequestId());
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            // Delete event request
            eventRequestService.deleteEventRequestById(detail.getEventRequestId());
            // Insert into ticket
            ticketService.saveTicket(createTicket(detail, pathQr, DbConstant.TICKET_FALSE, nameTicket));
        }

        eventRequestDetailService.deleteEventRequestDetail(eventRequestDetailId);
        log.info("Delete event request detail success id: {}", eventRequestDetailId);
    }


    private Ticket createTicket(EventRequestDetail detail, String pathQR, Integer status, String nameTicket) {
        Ticket ticket = new Ticket();
        ticket.setProviderId(detail.getProviderId());
        ticket.setTicketEventId(detail.getTicketEventId());
        ticket.setEventId(detail.getEventId());
        ticket.setPathQr(pathQR);
        log.info("PATH QR: " + pathQR);
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
