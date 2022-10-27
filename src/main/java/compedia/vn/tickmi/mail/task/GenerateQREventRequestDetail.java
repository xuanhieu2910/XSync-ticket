package compedia.vn.tickmi.mail.task;

import com.antkorwin.xsync.XSync;
import compedia.vn.tickmi.mail.entity.*;
import compedia.vn.tickmi.mail.repository.EventRepository;
import compedia.vn.tickmi.mail.repository.EventRequestHisRepository;
import compedia.vn.tickmi.mail.service.EventRequestDetailService;
import compedia.vn.tickmi.mail.service.EventRequestService;
import compedia.vn.tickmi.mail.service.MailRequestService;
import compedia.vn.tickmi.mail.service.TicketService;
import compedia.vn.tickmi.mail.task.qr.GenerateQR;
import compedia.vn.tickmi.mail.utils.DbConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;


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
    private EventRequestHisRepository eventRequestHisRepository;
    private EventRepository eventRepository;


    public GenerateQREventRequestDetail(EventRequestDetail detail, EventRequestService eventRequestService,
                                         EventRequestDetailService eventRequestDetailService, MailRequestService mailRequestService,
                                         TicketService ticketService,
                                         XSync<Long> xSync,EventRequestHisRepository eventRequestHisRepository,
                                         EventRepository eventRepository) {
        this.detail = detail;
        this.eventRequestService = eventRequestService;
        this.eventRequestDetailService = eventRequestDetailService;
        this.mailRequestService = mailRequestService;
        this.ticketService = ticketService;
        this.xSync = xSync;
        this.eventRequestHisRepository = eventRequestHisRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run() {
        Long eventRequestDetailId = detail.getId();
        log.info(" Event request detail id : " + eventRequestDetailId);
        String nameTicket = "EV_" + detail.getObjectId() + detail.getType() + detail.getIndexTicket();
        try {
            handleSyncTicket(nameTicket);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            handleSyncTicketFalse(nameTicket);
        }
        eventRequestDetailService.deleteEventRequestDetail(eventRequestDetailId);
        log.info("Delete event request detail success id: {}", eventRequestDetailId);
    }


    @Transactional
    public void handleSyncTicket(String nameTicket) {
        xSync.execute(detail.getEventRequestId(), () -> {
            String pathQr = GenerateQR.handlerGeneratePathQR(detail.getCodeTicket(), detail.getEventId(), nameTicket);
            Ticket ticket = createTicket(detail, pathQr, DbConstant.TICKET_NOT_CHECKIN, nameTicket);
            ticketService.saveTicket(ticket);
            log.info("SAVE: ticket service success id {}",ticket.getTicketId());

            eventRepository.updateTotalGenTicketEvent(detail.getEventId());

            log.info("UPDATE: Update total gen ticket success by event id {}",detail.getEventId());
            eventRequestService.updateEventRequestByIdEventRequestDetail(detail.getEventRequestId());
            EventRequest eventRequest = eventRequestService.findEventRequestById(detail.getEventRequestId()).orElse(null);
            if (null == eventRequest) {
                return;
            }
            log.info("Update event request quantity gen : {}", eventRequest.getTicketGeneration());
            if (eventRequest.getQuantity().equals(eventRequest.getTicketGeneration())) {
                log.info("Quantity: " + eventRequest.getQuantity() + " - " + eventRequest.getTicketGeneration());

                MailRequest mailRequest = createMailRequest(eventRequest);
                mailRequestService.saveMailRoot(mailRequest);
                log.info("SAVE: Mail Request success {}",mailRequest.toString());

                eventRequestService.deleteEventRequestById(detail.getEventRequestId());
                log.info("DELETE: Event Request success id: " + detail.getEventRequestId());

                EventRequestHis eventRequestHis = createEventRequestHis(eventRequest,1);
                eventRequestHisRepository.save(eventRequestHis);
                log.info("SAVE: Event request HIS {}", eventRequestHis.toString());

                eventRequestService.updateStatusGenTicket(mailRequest.getObjectId(),mailRequest.getType(),
                        DbConstant.STATUS_PROVED_SUCCESS);
                log.info("UPDATE: Status gen ticket success " + mailRequest.getObjectId() + "- type: " +
                        mailRequest.getType() + " - status: " + DbConstant.STATUS_PROVED_SUCCESS);

            }
        });
    }

    @Transactional
    public void handleSyncTicketFalse (String nameTicket) {
        eventRequestService.deleteEventRequestById(detail.getEventRequestId());
        log.error("CATCH: Delete Event Request by id: {} success!",detail.getEventRequestId());

        Ticket ticket = createTicket(detail, null, DbConstant.TICKET_FALSE, nameTicket);
        ticketService.saveTicket(ticket);
        log.error("CATCH: Save ticket {}",ticket.toString());

        eventRequestService.updateStatusGenTicket(ticket.getObjectId(),ticket.getType(),
                DbConstant.STATUS_PROVED_FALSE);
        log.info("CATCH : Update status gen ticket success " + ticket.getObjectId() + "- type: " +
                ticket.getType() + " - status: " + DbConstant.STATUS_PROVED_FALSE);
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
        if (null != eventRequest.getNote()) {
            mailRequest.setNote(eventRequest.getNote());
        }
        return mailRequest;
    }


    /**
     * @param status : 1. Success
     *                -1. False
     * */
    private EventRequestHis createEventRequestHis (EventRequest eventRequest,Integer status) {
        EventRequestHis his = new EventRequestHis();
        his.setStatus(status);
        his.setQuantity(eventRequest.getQuantity());
        his.setEventId(eventRequest.getEventId());
        his.setTicketEventId(eventRequest.getTicketEventId());
        his.setObjectId(eventRequest.getObjectId());
        his.setType(eventRequest.getType());
        his.setProviderId(eventRequest.getProviderId());
        his.setTicketGen(eventRequest.getTicketGeneration());
        his.setNameGuest(eventRequest.getNameGuest());
        his.setPhoneGuest(eventRequest.getPhoneGuest());
        his.setEmailGuest(eventRequest.getEmailGuest());
        his.setIdEventRequest(eventRequest.getId());
        his.setNote(eventRequest.getNote());
        return his;
    }

}
