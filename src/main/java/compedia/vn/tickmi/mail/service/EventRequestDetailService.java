package compedia.vn.tickmi.mail.service;

import compedia.vn.tickmi.mail.entity.EventRequestDetail;
import compedia.vn.tickmi.mail.repository.EventRequestDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRequestDetailService {

    private final static Logger logger = LoggerFactory.getLogger(EventRequestDetailService.class);


    @Autowired
    EventRequestDetailRepository eventRequestRepositoryDetail;

    public List<EventRequestDetail> getAllEventRequestDetailLimit() {
        logger.debug("Service start get all event request detail limit");
        return eventRequestRepositoryDetail.getAllEventRequestDetailCustom();
    }

    public void saveEventRequestDetails(List<EventRequestDetail> eventRequestDetails) {
        logger.debug("Service start save all event request detail");
        eventRequestRepositoryDetail.saveAll(eventRequestDetails);
    }

    public void saveEventRequestDetail(EventRequestDetail eventRequestDetail) {
        logger.debug("Service start save event request detail");
        eventRequestRepositoryDetail.save(eventRequestDetail);
    }

    public void deleteEventRequestDetail(EventRequestDetail eventRequestDetail) {
        logger.debug("Service start delete event request detail");
        eventRequestRepositoryDetail.delete(eventRequestDetail);
    }

    public void deleteEventRequestDetails(List<EventRequestDetail> eventRequestDetails) {
        logger.debug("Service start delete all event request detail");
        eventRequestRepositoryDetail.deleteAll(eventRequestDetails);
    }

    public List<EventRequestDetail> requestDetails(Integer eventRequestId) {
        return eventRequestRepositoryDetail.getAllEventRequestByIdEventRequest(eventRequestId);
    }

    public List<EventRequestDetail> getAllEventRequestByIdEventRequestAndStatus(Integer eventRequestId, Integer status) {
        return eventRequestRepositoryDetail.getAllEventRequestByIdEventRequest(eventRequestId);
    }

}
