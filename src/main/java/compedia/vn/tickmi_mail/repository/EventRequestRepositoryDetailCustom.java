package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.EventRequestDetail;

import java.util.List;

public interface EventRequestRepositoryDetailCustom {

    List<EventRequestDetail> getAllEventRequestDetailCustom (Integer limits, Integer status,Integer status2,Integer retry);

    List<EventRequestDetail> getAllEventRequestByIdEventRequest (Long eventRequestId);

    List<EventRequestDetail> getAllEventRequestByIdEventRequestAndStatus (Long eventRequestId, Integer status);


}
