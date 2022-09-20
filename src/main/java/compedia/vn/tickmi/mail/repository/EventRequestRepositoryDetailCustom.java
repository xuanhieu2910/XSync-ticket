package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.EventRequestDetail;

import java.util.List;

public interface EventRequestRepositoryDetailCustom {

    List<EventRequestDetail> getAllEventRequestDetailCustom();

    List<EventRequestDetail> getAllEventRequestByIdEventRequest(Integer eventRequestId);

    List<EventRequestDetail> getAllEventRequestByIdEventRequestAndStatus(Integer eventRequestId, Integer status);

}
