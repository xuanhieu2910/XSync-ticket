package compedia.vn.tickmi.mail.task.ticket;

import com.antkorwin.xsync.XSync;
import compedia.vn.tickmi.mail.dto.EventDto;
import compedia.vn.tickmi.mail.entity.EventRequest;
import compedia.vn.tickmi.mail.entity.EventRequestDetail;
import compedia.vn.tickmi.mail.repository.*;
import compedia.vn.tickmi.mail.service.EventRequestDetailService;
import compedia.vn.tickmi.mail.service.EventRequestService;
import compedia.vn.tickmi.mail.service.MailRequestService;
import compedia.vn.tickmi.mail.service.TicketService;
import compedia.vn.tickmi.mail.task.CreateEventRequestDetail;
import compedia.vn.tickmi.mail.task.GenerateQREventRequestDetail;
import compedia.vn.tickmi.mail.utils.DbConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Autowired
    EventRequestHisRepository eventRequestHisRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    RegisterTicketRepository registerTicketRepository;

    @Autowired
    XSync<Long> xSync;

    @Autowired
    SeatRepository seatRepository;

    private static final Queue<EventRequest> queueEventRequest = new ConcurrentLinkedQueue<>();
    private static final Queue<EventRequestDetail> queueEventRequestDetails = new ConcurrentLinkedQueue<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    /**
     * Method to get data from DB EVENT_REQUEST -> push queue to handle process other
     */
    @Scheduled(fixedRate = 2500)
    public void getEventRequestsLoop() throws InterruptedException {
        if (DbConstant.IS_FLAT_RUN_JOB) {
            try {
                List<EventRequest> eventRequestList = eventRequestService.getEventRequestList();
                if (!CollectionUtils.isEmpty(eventRequestList)) {
                    String object = eventRequestList.toString();
                    log.info("GET: from Database EVENT_REQUEST with Size: " + eventRequestList.size() + "- Detail: "+ object);
                    // Update n object
                    eventRequestService.updateEventRequestByStatus(eventRequestList, DbConstant.STATUS_EVENT_REQUEST);
                    log.info("UPDATE: Update Status EVENT_REQUEST success: " + object);
                    // Push n object to queue
                    queueEventRequest.addAll(eventRequestList);
                    log.info("PUSH: Push event request to queue event request success!");
                }
            } catch (Exception e) {
                log.error("Error to get event request", e);
            }
        }
    }

    /**
     * Method to handle from queue -> Set value -> Insert value to db EVENT_REQUEST_DETAIL
     */
    @Scheduled(fixedRate = 10)
    public void insertCacheEventRequestDetail() {
        if (!queueEventRequest.isEmpty()) {
            EventRequest eventRequest = queueEventRequest.poll();
            Runnable worker = new CreateEventRequestDetail(eventRequest, eventRequestDetailService);
            executor.execute(worker);
        }
    }
    /***
     * Method to get event request detail -> set value -> push queue to handle process other
     */
    @Scheduled(fixedRate = 2500)
    public void getEventRequestDetailLoop() {
        if (DbConstant.IS_FLAT_RUN_JOB) {
            try {
                // Get n object
                List<EventRequestDetail> eventRequestDetails = eventRequestDetailService.getAllEventRequestDetailLimit();
                // update object
                if (!CollectionUtils.isEmpty(eventRequestDetails)) {
                    String object = eventRequestDetails.toString();
                    log.info("GET: Get from EVENT_REQUEST_DETAIL with size: " + eventRequestDetails.size() + " - Detail: " + object);
                    // create event request details
                    eventRequestDetails.stream().forEach(x -> x.setStatus(DbConstant.STATUS_EVENT_REQUEST_DETAIL));
                    eventRequestDetailService.saveEventRequestDetails(eventRequestDetails);
                    log.info("UPDATE: Update Status EVENT_REQUEST success: " + object);
                    // Insert queue
                    queueEventRequestDetails.addAll(eventRequestDetails);
                    log.info("PUSH: Push event request detail to QUEUE event request detail success!");
                }
            } catch (Exception e) {
                log.error("Error to get event request detail", e);
            }
        }
    }

    /**
     * Handle to get data from event_request_detail -> process -> generate path QR
     */
    @Scheduled(fixedRate = 10)
    public void generateQRPathImage() {
        if (!queueEventRequestDetails.isEmpty()) {
            EventRequestDetail detail = queueEventRequestDetails.poll();
            if ( null == detail) {
                return;
            }

            EventDto eventDto = eventRepository.getTotalGenTicket(detail.getEventId());
            if (eventDto == null) {
                return;
            }

            int quantityLength = eventDto.getTotalQuantity() == null ? 1 : String.valueOf(eventDto.getTotalQuantity()).length();
            int index = eventDto.getTotalGenTicketCreated() == null ? 1 : eventDto.getTotalGenTicketCreated() + 1;
            String nameTicket = String.format("%0" + quantityLength + "d", index);
            if (detail.getEventId() == 2448) {
                nameTicket = detail.getNote();
            } else if (detail.getEventId() == 2463) {
                String stt = registerTicketRepository.getStt(detail.getObjectId());
                nameTicket = "NSD24-" + String.format("%04d", Long.valueOf(stt));
            } else if (detail.getEventId() == 4562) {
                if (detail.getNote() != null && detail.getNote().contains("_")) {
                    nameTicket = detail.getNote().substring(0, detail.getNote().indexOf("_"));
                }
            }

            eventRepository.updateTotalGenTicketEvent(detail.getEventId(), 1);
            log.info("UPDATE: Update total gen ticket success by event id {}", detail.getEventId());

            Runnable worker = new GenerateQREventRequestDetail(detail, nameTicket, eventRequestService,eventRequestDetailService,
                     mailRequestService, ticketService, xSync, eventRequestHisRepository, eventRepository, seatRepository);
            executor.execute(worker);
        }
    }
}
