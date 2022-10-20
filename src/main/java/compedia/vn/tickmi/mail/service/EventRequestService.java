package compedia.vn.tickmi.mail.service;

import compedia.vn.tickmi.mail.entity.EventRequest;
import compedia.vn.tickmi.mail.repository.EventRequestRepository;
import compedia.vn.tickmi.mail.utils.DbConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
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

    public Optional<EventRequest> findEventRequestById(Long id) {
        return eventRequestRepository.findEventRequestByIdEventRequest(id);
    }

    public void updateEventRequestByIdEventRequestDetail(Long idEventRequestDetail) {
        eventRequestRepository.autoUpdateQuantityGenById(idEventRequestDetail);
    }

    public void deleteEventRequest(EventRequest eventRequest) {
        eventRequestRepository.delete(eventRequest);
    }

    public void deleteEventRequestById(Long id) {
        eventRequestRepository.deleteByIdCustom(id);
    }


    /**
     *  @param objectId : Identity the Object
     *         type:  1. Order detail
     *                2. Guest detail
     *                3. Register ticket detail
     * */
    public void updateStatusGenTicket (Long objectId, Integer type,Integer status) {
        if (type.equals(1)) {
            eventRequestRepository.updateStatusOrderDetail(objectId,status);
            log.info("Update status order");
        }
        else if (type.equals(2)) {
            eventRequestRepository.updateStatusGuestDetail(objectId,status);
            log.info("Update status guest");
        }
        else if (type.equals(3)) {
            eventRequestRepository.updateStatusRegisterDetail(objectId,status);
            log.info("Update status register");
        }
    }
}
