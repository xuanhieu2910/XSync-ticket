package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.EventRequestDetail;

import java.util.List;

public interface EventRequestRepositoryDetailCustom {

    List<EventRequestDetail> getAllEventRequestDetailCustom ();

    List<EventRequestDetail> getAllEventRequestByIdEventRequest (Integer eventRequestId);

    List<EventRequestDetail> getAllEventRequestByIdEventRequestAndStatus (Integer eventRequestId, Integer status);


}
