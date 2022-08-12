package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.EventRequest;

import java.util.List;
import java.util.Optional;

public interface EventRequestRepositoryCustom {

        void deleteEventsRequest (List<Long> eventRequestIds);
        List<EventRequest> getEventRequestByStatus (Integer status, Integer limit);
}
