package compedia.vn.tickmi_mail.service;

import compedia.vn.tickmi_mail.entity.EventRequest;
import compedia.vn.tickmi_mail.repository.EventRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<EventRequest> findEventRequestById (Long id, Integer status) {
        return eventRequestRepository.findEventRequestById(id,status);
    }

    public void updateEventRequest (EventRequest eventRequest) {
        eventRequestRepository.save(eventRequest);
        return;
    }

}
