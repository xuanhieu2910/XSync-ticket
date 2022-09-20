package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.EventRequest;

import java.util.List;
import java.util.Optional;

public interface EventRequestRepositoryCustom {

    List<EventRequest> getEventRequestByStatus();

    Optional<EventRequest> findEventRequestByIdAndStatus(Integer id, Integer status);

    Optional<EventRequest> findEventRequestById(Integer id);

    void updateEventRequestById(Integer id, Integer status);
}
