package compedia.vn.tickmi.mail.service;

import compedia.vn.tickmi.mail.entity.EventRequest;
import compedia.vn.tickmi.mail.repository.EventRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventRequestService {


    @Autowired
    EventRequestRepository eventRequestRepository;


    public List<EventRequest> getEventRequestList() {
        List<EventRequest> eventRequests = eventRequestRepository.getEventRequestByStatus();
        return eventRequests;
    }

    public List<EventRequest> updateEventRequestByStatus(List<EventRequest> eventRequests, Integer status) {
        eventRequests.stream().forEach(x -> x.setStatus(status));
        return eventRequestRepository.saveAll(eventRequests);
    }

    public Optional<EventRequest> findEventRequestById(Integer id) {
        return eventRequestRepository.findEventRequestByIdEventRequest(id);
    }

    public void updateEventRequestByIdEventRequestDetail(Integer idEventRequestDetail) {
        eventRequestRepository.autoUpdateQuantityGenById(idEventRequestDetail);
    }

    public void deleteEventRequest(EventRequest eventRequest) {
        eventRequestRepository.delete(eventRequest);
    }

    public void deleteEventRequestById(Integer id) {
        eventRequestRepository.deleteById(id);
    }

}
