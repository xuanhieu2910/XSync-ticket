package compedia.vn.tickmi_mail.repository.impl;

import compedia.vn.tickmi_mail.entity.EventRequestDetail;
import compedia.vn.tickmi_mail.repository.EventRequestRepositoryDetailCustom;
import compedia.vn.tickmi_mail.utils.ValueUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class EventRequestDetailRepositoryImpl implements EventRequestRepositoryDetailCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EventRequestDetail> getAllEventRequestDetailCustom(Integer limits, Integer status,Integer status2,Integer retry) {
        log.debug("Start query find event request detail");
        Query query = entityManager.createNativeQuery(SQL_GetAllEventRequestDetailCustom);
        query.setParameter("status_1",status);
        query.setParameter("status_2",status2);
        query.setParameter("sizeRetry",retry);
        query.setParameter("limit",limits);
        List<Object[]> result = query.getResultList();
        List<EventRequestDetail> response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                EventRequestDetail detail = new EventRequestDetail();
                detail.setId(ValueUtil.getIntegerByObject(obj[0]));
                detail.setPathImage(ValueUtil.getStringByObject(obj[1]) == null ? null : ValueUtil.getStringByObject(obj[1]));
                detail.setIndexTicket(ValueUtil.getIntegerByObject(obj[2]));
                detail.setCodeTicket(ValueUtil.getStringByObject(obj[3]));
                detail.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                detail.setRetry(ValueUtil.getIntegerByObject(obj[5]));
                detail.setObjectContent(ValueUtil.getStringByObject(obj[6]) == null ? null : ValueUtil.getStringByObject(obj[6]));
                detail.setEventRequestId(ValueUtil.getIntegerByObject(obj[7]));
                detail.setGuestId(ValueUtil.getIntegerByObject(obj[8]));
                detail.setEventId(ValueUtil.getIntegerByObject(obj[9]));
                detail.setTicketEventId(ValueUtil.getIntegerByObject(obj[10]));
                detail.setTimeGenerate(ValueUtil.getTimestampByObject(obj[11]) == null ? null : ValueUtil.getTimestampByObject(obj[11]));
                detail.setModifiedTime(ValueUtil.getTimestampByObject(obj[12]) == null ? null : ValueUtil.getTimestampByObject(obj[12]));
                detail.setProviderId(ValueUtil.getIntegerByObject(obj[13]));
                detail.setUserId(ValueUtil.getIntegerByObject(obj[14]));
                detail.setGuestCode(ValueUtil.getStringByObject(obj[15]));
                response.add(detail);
            }
        }
        return response;
    }



    @Override
    public List<EventRequestDetail> getAllEventRequestByIdEventRequest(Long eventRequestId) {
        log.debug("Start to query get all event request by id event request");
        Query query = entityManager.createNativeQuery(SQL_GetAllEventRequestByIdEventRequest);
        query.setParameter("idEventRequest",eventRequestId);
        List<Object[]> result = query.getResultList();
        List<EventRequestDetail> response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                EventRequestDetail detail = new EventRequestDetail();
                detail.setId(ValueUtil.getIntegerByObject(obj[0]));
                detail.setPathImage(ValueUtil.getStringByObject(obj[1]) == null ? null : ValueUtil.getStringByObject(obj[1]));
                detail.setIndexTicket(ValueUtil.getIntegerByObject(obj[2]));
                detail.setCodeTicket(ValueUtil.getStringByObject(obj[3]));
                detail.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                detail.setRetry(ValueUtil.getIntegerByObject(obj[5]));
                detail.setObjectContent(ValueUtil.getStringByObject(obj[6]) == null ? null : ValueUtil.getStringByObject(obj[6]));
                detail.setEventRequestId(ValueUtil.getIntegerByObject(obj[7]));
                detail.setGuestId(ValueUtil.getIntegerByObject(obj[8]));
                detail.setEventId(ValueUtil.getIntegerByObject(obj[9]));
                detail.setTicketEventId(ValueUtil.getIntegerByObject(obj[10]));
                detail.setTimeGenerate(ValueUtil.getTimestampByObject(obj[11]) == null ? null : ValueUtil.getTimestampByObject(obj[11]));
                detail.setModifiedTime(ValueUtil.getTimestampByObject(obj[12]) == null ? null : ValueUtil.getTimestampByObject(obj[12]));
                detail.setProviderId(ValueUtil.getIntegerByObject(obj[13]));
                detail.setUserId(ValueUtil.getIntegerByObject(obj[14]));
                detail.setGuestCode(ValueUtil.getStringByObject(obj[15]));
                response.add(detail);
            }
        }
        return response;
    }

    @Override
    public List<EventRequestDetail> getAllEventRequestByIdEventRequestAndStatus(Long eventRequestId, Integer status) {
        log.debug("get all event request by id event request and status");
        Query query = entityManager.createNativeQuery(SQL_GetAllEventRequestByIdEventRequestAndStatus);
        query.setParameter("idEventRequest",eventRequestId);
        List<Object[]> result = query.getResultList();
        List<EventRequestDetail> response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                EventRequestDetail detail = new EventRequestDetail();
                detail.setId(ValueUtil.getIntegerByObject(obj[0]));
                detail.setPathImage(ValueUtil.getStringByObject(obj[1]) == null ? null : ValueUtil.getStringByObject(obj[1]));
                detail.setIndexTicket(ValueUtil.getIntegerByObject(obj[2]));
                detail.setCodeTicket(ValueUtil.getStringByObject(obj[3]));
                detail.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                detail.setRetry(ValueUtil.getIntegerByObject(obj[5]));
                detail.setObjectContent(ValueUtil.getStringByObject(obj[6]) == null ? null : ValueUtil.getStringByObject(obj[6]));
                detail.setEventRequestId(ValueUtil.getIntegerByObject(obj[7]));
                detail.setGuestId(ValueUtil.getIntegerByObject(obj[8]));
                detail.setEventId(ValueUtil.getIntegerByObject(obj[9]));
                detail.setTicketEventId(ValueUtil.getIntegerByObject(obj[10]));
                detail.setTimeGenerate(ValueUtil.getTimestampByObject(obj[11]) == null ? null : ValueUtil.getTimestampByObject(obj[11]));
                detail.setModifiedTime(ValueUtil.getTimestampByObject(obj[12]) == null ? null : ValueUtil.getTimestampByObject(obj[12]));
                detail.setProviderId(ValueUtil.getIntegerByObject(obj[13]));
                detail.setUserId(ValueUtil.getIntegerByObject(obj[14]));
                detail.setGuestCode(ValueUtil.getStringByObject(obj[15]));
                response.add(detail);
            }
        }
        return response;
    }

    private static String SQL_GetAllEventRequestDetailCustom = "SELECT details.ID_DETAILS," +
            "       PATH_IMAGE," +
            "       INDEX_TICKET," +
            "       CODE_TICKET," +
            "       STATUS," +
            "       RETRY," +
            "       OBJECT_CONTENT," +
            "       ID_EVENT_REQUEST," +
            "       GUEST_ID," +
            "       EVENT_ID," +
            "       TICKET_EVENT_ID," +
            "       TIME_GENERATE," +
            "       MODIFIED_TIME," +
            "       PROVIDER_ID," +
            "       USER_ID," +
            "       GUEST_CODE" +
            " FROM EVENT_REQUEST_DETAILS details" +
            " WHERE (" +
            "        (details.STATUS = :status_1 AND details.RETRY <= :sizeRetry) OR" +
            "        (details.STATUS = :status_2 AND details.RETRY <= :sizeRetry)" +
            "    )" +
            "  AND ROWNUM < :limit";

    private static String SQL_GetAllEventRequestByIdEventRequest = "SELECT detail.ID_DETAILS, +" +
            "   detail.PATH_IMAGE, +" +
            "   detail.INDEX_TICKET, +" +
            "   detail.CODE_TICKET, +" +
            "   detail.STATUS, +" +
            "   detail.RETRY, +" +
            "   detail.OBJECT_CONTENT, +" +
            "   detail.ID_EVENT_REQUEST, +" +
            "   detail.GUEST_ID, +" +
            "   detail.EVENT_ID, +" +
            "   detail.TICKET_EVENT_ID, +" +
            "   detail.TIME_GENERATE, +" +
            "   detail.MODIFIED_TIME, +" +
            "   detail.PROVIDER_ID, +" +
            "   detail.USER_ID, +" +
            "   detail.GUEST_CODE +" +
            "                 FROM EVENT_REQUEST eventRequest +" +
            "     inner join EVENT_REQUEST_DETAILS detail on eventRequest.ID_EVENT_REQUEST = detail.ID_EVENT_REQUEST +" +
            "                 WHERE eventRequest.ID_EVENT_REQUEST = :idEventRequest";

    private static String SQL_GetAllEventRequestByIdEventRequestAndStatus = "SELECT detail.ID_DETAILS, +" +
            "   detail.PATH_IMAGE, +" +
            "   detail.INDEX_TICKET, +" +
            "   detail.CODE_TICKET, +" +
            "   detail.STATUS, +" +
            "   detail.RETRY, +" +
            "   detail.OBJECT_CONTENT, +" +
            "   detail.ID_EVENT_REQUEST, +" +
            "   detail.GUEST_ID, +" +
            "   detail.EVENT_ID, +" +
            "   detail.TICKET_EVENT_ID, +" +
            "   detail.TIME_GENERATE, +" +
            "   detail.MODIFIED_TIME, +" +
            "   detail.PROVIDER_ID, +" +
            "   detail.USER_ID, +" +
            "   detail.GUEST_CODE +" +
            "                 FROM EVENT_REQUEST eventRequest +" +
            "     inner join EVENT_REQUEST_DETAILS detail on eventRequest.ID_EVENT_REQUEST = detail.ID_EVENT_REQUEST +" +
            "                 WHERE eventRequest.ID_EVENT_REQUEST = :idEventRequest";

}
