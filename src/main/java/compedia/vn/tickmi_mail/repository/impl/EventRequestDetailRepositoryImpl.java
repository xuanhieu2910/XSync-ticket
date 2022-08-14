package compedia.vn.tickmi_mail.repository.impl;

import compedia.vn.tickmi_mail.entity.EventRequestDetail;
import compedia.vn.tickmi_mail.repository.EventRequestRepositoryDetailCustom;
import compedia.vn.tickmi_mail.utils.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EventRequestDetailRepositoryImpl implements EventRequestRepositoryDetailCustom {

    private final static Logger logger = LoggerFactory.getLogger(EventRequestDetailRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EventRequestDetail> getAllEventRequestDetailCustom(Integer limits, Integer status,Integer status2,Integer retry) {
        logger.debug("Start query find event request detail");
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT details.*" +
                " FROM EVENT_REQUEST_DETAILS details" +
                " WHERE ((details.STATUS = :status_1 AND RETRY <= :sizeRetry) OR (details = :status_2 AND RETRY <= :sizeRetry))" +
                "  AND ROWNUM < :limit");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status_1",status);
        query.setParameter("status_2",status2);
        query.setParameter("sizeRetry",retry);
        query.setParameter("limit",limits);
        List<Object[]> result = query.getResultList();
        List<EventRequestDetail> response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                EventRequestDetail detail = new EventRequestDetail();
                detail.setId(ValueUtil.getLongByObject(obj[0]));
                detail.setPathImage(ValueUtil.getStringByObject(obj[1]) == null ? null : ValueUtil.getStringByObject(obj[1]));
                detail.setIndexTicket(ValueUtil.getIntegerByObject(obj[2]));
                detail.setCodeTicket(ValueUtil.getStringByObject(obj[3]));
                detail.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                detail.setRetry(ValueUtil.getIntegerByObject(obj[5]));
                detail.setObjectContent(ValueUtil.getStringByObject(obj[6]) == null ? null : ValueUtil.getStringByObject(obj[6]));
                detail.setEventRequestId(ValueUtil.getLongByObject(obj[7]));
                detail.setEventId(ValueUtil.getLongByObject(obj[8]));
                detail.setTicketEventId(ValueUtil.getLongByObject(obj[9]));
                detail.setGuestId(ValueUtil.getLongByObject(obj[10]));
                detail.setTimeGenerate(ValueUtil.getTimestampByObject(obj[11]) == null ? null : ValueUtil.getTimestampByObject(obj[11]));
                detail.setModifiedTime(ValueUtil.getTimestampByObject(obj[12]) == null ? null : ValueUtil.getTimestampByObject(obj[12]));
                detail.setProviderId(ValueUtil.getLongByObject(obj[13]));
                response.add(detail);
            }
        }
        return response;
    }

    @Override
    public List<EventRequestDetail> getAllEventRequestByIdEventRequest(Long eventRequestId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT detail.* " +
                " FROM EVENT_REQUEST eventRequest" +
                "      inner join EVENT_REQUEST_DETAILS detail on eventRequest.ID_EVENT_REQUEST = detail.ID_EVENT_REQUEST" +
                " WHERE eventRequest.ID_EVENT_REQUEST = :id ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("id",eventRequestId);
        List<Object[]> result = query.getResultList();
        List<EventRequestDetail> response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                EventRequestDetail detail = new EventRequestDetail();
                detail.setId(ValueUtil.getLongByObject(obj[0]));
                detail.setPathImage(ValueUtil.getStringByObject(obj[1]) == null ? null : ValueUtil.getStringByObject(obj[1]));
                detail.setIndexTicket(ValueUtil.getIntegerByObject(obj[2]));
                detail.setCodeTicket(ValueUtil.getStringByObject(obj[3]));
                detail.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                detail.setRetry(ValueUtil.getIntegerByObject(obj[5]));
                detail.setObjectContent(ValueUtil.getStringByObject(obj[6]) == null ? null : ValueUtil.getStringByObject(obj[6]));
                detail.setEventRequestId(ValueUtil.getLongByObject(obj[7]));
                detail.setEventId(ValueUtil.getLongByObject(obj[8]));
                detail.setTicketEventId(ValueUtil.getLongByObject(obj[9]));
                detail.setGuestId(ValueUtil.getLongByObject(obj[10]));
                detail.setTimeGenerate(ValueUtil.getTimestampByObject(obj[11]) == null ? null : ValueUtil.getTimestampByObject(obj[11]));
                detail.setModifiedTime(ValueUtil.getTimestampByObject(obj[12]) == null ? null : ValueUtil.getTimestampByObject(obj[12]));
                detail.setProviderId(ValueUtil.getLongByObject(obj[13]));
                response.add(detail);
            }
        }
        return response;
    }

}
