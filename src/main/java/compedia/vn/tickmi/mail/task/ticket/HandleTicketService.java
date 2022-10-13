package compedia.vn.tickmi.mail.task.ticket;

import com.antkorwin.xsync.XSync;
import compedia.vn.tickmi.mail.entity.EventRequest;
import compedia.vn.tickmi.mail.entity.EventRequestDetail;
import compedia.vn.tickmi.mail.repository.ProviderRepository;
import compedia.vn.tickmi.mail.service.EventRequestDetailService;
import compedia.vn.tickmi.mail.service.EventRequestService;
import compedia.vn.tickmi.mail.service.MailRequestService;
import compedia.vn.tickmi.mail.service.TicketService;
import compedia.vn.tickmi.mail.task.CreateEventRequestDetail;
import compedia.vn.tickmi.mail.task.GenerateQREventRequestDetail;
import compedia.vn.tickmi.mail.utils.DbConstant;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@EnableScheduling
@Log4j2
@EnableAsync
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
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
    XSync<Long> xSync;

    private static final Queue<EventRequest> queueEventRequest = new ConcurrentLinkedQueue<>();
    private static final Queue<EventRequestDetail> queueEventRequestDetails = new ConcurrentLinkedQueue<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(50);
    /**
     * Method to get data from DB EVENT_REQUEST -> push queue to handle process other
     */
    @Async
    @Scheduled(fixedRate = 3000)
    public void getEventRequestsLoop() throws InterruptedException {
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
    @Scheduled(fixedRate = 10)
    public void insertCacheEventRequestDetail() {
        if (!queueEventRequest.isEmpty()) {
            EventRequest eventRequest = queueEventRequest.poll();
            for (int i = 0; i < 1; i++) {
                Runnable worker = new CreateEventRequestDetail(eventRequest,eventRequestDetailService);
                executor.execute(worker);
            }
        }
    }
    /***
     * Method to get event request detail -> set value -> push queue to handle process other
     */
    @Scheduled(fixedRate = 3000)
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
    @Scheduled(fixedRate = 10)
    public void generateQRPathImage(){
        if (!queueEventRequestDetails.isEmpty()) {
            EventRequestDetail detail = queueEventRequestDetails.poll();
            if ( null == detail) {
                return;
            }
            // Success and remove in event detail
            Long id = detail.getId();
            log.info("Id event request detail: " + id);
             Runnable worker = new GenerateQREventRequestDetail(detail,eventRequestService,eventRequestDetailService, mailRequestService,ticketService, xSync);
             executor.execute(worker);
        }
    }
}
