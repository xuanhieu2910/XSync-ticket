package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.EventRequestDetail;

import java.util.List;

public interface EventRequestRepositoryDetailCustom {

    List<EventRequestDetail> findEventRequestDetailsLimit (Integer limits, Integer status);

    void updateEventRequestDetails(List<EventRequestDetail> details);
}
