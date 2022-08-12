package compedia.vn.tickmi_mail.task.mail;

import compedia.vn.tickmi_mail.entity.EventRequest;
import compedia.vn.tickmi_mail.entity.EventRequestDetail;
import compedia.vn.tickmi_mail.repository.EventRequestRepositoryDetail;
import compedia.vn.tickmi_mail.service.ticket.EventRequestService;
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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Component
@EnableScheduling
@EnableAsync
public class HandleMailService {

    private final static Logger logger = LoggerFactory.getLogger(HandleMailService.class);

    private static final Queue<EventRequestDetail> queueEventRequestDetails = new ArrayDeque<>();
    private static final Queue<EventRequest> queueEventRequest = new ArrayDeque<>();

    @Autowired
    private EventRequestService eventRequestService;

    @Autowired
    private EventRequestRepositoryDetail detailRepository;

    // Get n object
    @Async
    @Scheduled(fixedRate = 3000)
    public void getEventRequestsLoop() {
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
    }


    @Async
    @Scheduled(fixedRate = 100)
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
                    details.add(dto);
                }
                    detailRepository.saveAll(details);
            } catch (Exception e) {
                logger.error("Lỗi nè:" + e.getMessage());
            }
        }
    }



    public void generateQRPathImage () {

    }
    // Get n object
    // Create path image
    // Success : Update  Path + flat -> Update event request
    // False : Update flat

}
