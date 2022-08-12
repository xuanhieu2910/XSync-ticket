package compedia.vn.tickmi_mail.service.ticket;

import compedia.vn.tickmi_mail.entity.EventRequest;
import compedia.vn.tickmi_mail.repository.EventRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRequestService {


    @Autowired
    EventRequestRepository eventRequestRepository;


    public List<EventRequest> getEventRequestList (Integer status, Integer limit) {
        List<EventRequest>eventRequests = eventRequestRepository.getEventRequestByStatus(status,limit);
        return eventRequests;
    }

    public List<EventRequest> updateEventRequestByStatus (List<EventRequest> eventRequests,Integer status) {
        eventRequests.stream().forEach(x->x.setStatus(status));
        return eventRequestRepository.saveAll(eventRequests);
    }

}
