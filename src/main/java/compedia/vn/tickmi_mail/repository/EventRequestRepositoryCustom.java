package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.EventRequest;

import java.util.List;
import java.util.Optional;

public interface EventRequestRepositoryCustom {

        List<EventRequest> getEventRequestByStatus (Integer status, Integer limit);
        Optional<EventRequest> findEventRequestByIdAndStatus (Integer id,Integer status);
        Optional<EventRequest> findEventRequestById (Integer id);

        void updateEventRequestById (Integer id, Integer status);
}
