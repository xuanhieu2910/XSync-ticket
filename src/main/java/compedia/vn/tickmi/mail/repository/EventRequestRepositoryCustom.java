package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.EventRequest;

import java.util.List;
import java.util.Optional;

public interface EventRequestRepositoryCustom {

    List<EventRequest> getEventRequestByStatus();

    Optional<EventRequest> findEventRequestByIdEventRequest(Integer id);

    void autoUpdateQuantityGenById (Integer id);

}
