package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.EventRequest;

import java.util.List;
import java.util.Optional;

public interface EventRequestRepositoryCustom {

        List<EventRequest> getEventRequestByStatus (Integer status, Integer limit);
        Optional<EventRequest> findEventRequestByIdAndStatus (Long id,Integer status);
        Optional<EventRequest> findEventRequestById (Long id);

        void updateEventRequestById (Long id, Integer status);
}
