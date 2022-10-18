package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.EventRequest;

import java.util.List;
import java.util.Optional;

public interface EventRequestRepositoryCustom {

    List<EventRequest> getEventRequestByStatus();

    Optional<EventRequest> findEventRequestByIdEventRequest(Long id);

    void autoUpdateQuantityGenById (Long id);

    void deleteByIdCustom(Long id);

    void updateWholeEventRequestToNew ();

    void updateStatusOrderDetail (Long objectId,Integer status);

    void updateStatusGuestDetail (Long objectId,Integer status);

    void updateStatusRegisterDetail (Long objectId,Integer status);
}
