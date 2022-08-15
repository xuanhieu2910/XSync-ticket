package compedia.vn.tickmi_mail.repository.impl;

import compedia.vn.tickmi_mail.entity.EventRequest;
import compedia.vn.tickmi_mail.repository.EventRequestRepositoryCustom;
import compedia.vn.tickmi_mail.utils.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventRequestRepositoryImpl implements EventRequestRepositoryCustom {


    private static final Logger logger = LoggerFactory.getLogger(EventRequestRepositoryImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<EventRequest> getEventRequestByStatus(Integer status, Integer limit) {
        logger.debug("Query start get event request");
        StringBuilder sb = new StringBuilder();
        sb.append("select evenRequest.ID_EVENT_REQUEST," +
                "       GUEST_ID," +
                "       STATUS," +
                "       CREATE_TIME," +
                "       MODIFIED_TIME," +
                "       PROVIDER_ID," +
                "       QUANTITY," +
                "       EVENT_ID," +
                "       TICKET_EVENT_ID," +
                "       QUANTITY_GEN," +
                "       USER_ID," +
                "       GUEST_CODE" +
                " from EVENT_REQUEST evenRequest" +
                " where evenRequest.STATUS = :status" +
                "  and ROWNUM < :limitRow");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status",status);
        query.setParameter("limitRow",limit);
        List<Object[]> result = query.getResultList();
        List<EventRequest>response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                EventRequest dto = new EventRequest();
                dto.setId(ValueUtil.getLongByObject(obj[0]));
                dto.setGuestId(ValueUtil.getLongByObject(obj[1]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                dto.setCreateTime(ValueUtil.getTimestampByObject(obj[3]));
                dto.setModifiedTime(ValueUtil.getTimestampByObject(obj[4]));;
                dto.setProviderId(ValueUtil.getLongByObject(obj[5]));
                dto.setQuantity(ValueUtil.getLongByObject(obj[6]));
                dto.setEventId(ValueUtil.getLongByObject(obj[7]));
                dto.setTicketEventId(ValueUtil.getLongByObject(obj[8]));
                dto.setQuantityGen(ValueUtil.getIntegerByObject(obj[9]));
                dto.setUserId(ValueUtil.getLongByObject(obj[10]));
                dto.setGuestCode(ValueUtil.getStringByObject(obj[11]));
                response.add(dto);
            }
        }
        return response;
    }

    @Override
    public Optional<EventRequest> findEventRequestByIdAndStatus(Long id, Integer status) {
        logger.debug("Query start query find event request by id");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT eventRequest.ID_EVENT_REQUEST," +
                "       GUEST_ID," +
                "       STATUS," +
                "       CREATE_TIME," +
                "       MODIFIED_TIME," +
                "       PROVIDER_ID," +
                "       QUANTITY," +
                "       EVENT_ID," +
                "       TICKET_EVENT_ID," +
                "       QUANTITY_GEN," +
                "       USER_ID," +
                "       GUEST_CODE" +
                " FROM EVENT_REQUEST eventRequest" +
                " WHERE eventRequest.ID_EVENT_REQUEST = :id" +
                "  AND eventRequest.STATUS = :status");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("id",id);
        query.setParameter("status",status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            Object[] obj = result.get(0);
            EventRequest dto = new EventRequest();
            dto.setId(ValueUtil.getLongByObject(obj[0]));
            dto.setGuestId(ValueUtil.getLongByObject(obj[1]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
            dto.setCreateTime(ValueUtil.getTimestampByObject(obj[3]));
            dto.setModifiedTime(ValueUtil.getTimestampByObject(obj[4]));;
            dto.setProviderId(ValueUtil.getLongByObject(obj[5]));
            dto.setQuantity(ValueUtil.getLongByObject(obj[6]));
            dto.setEventId(ValueUtil.getLongByObject(obj[7]));
            dto.setTicketEventId(ValueUtil.getLongByObject(obj[8]));
            dto.setQuantityGen(ValueUtil.getIntegerByObject(obj[9]));
            dto.setUserId(ValueUtil.getLongByObject(obj[10]));
            dto.setGuestCode(ValueUtil.getStringByObject(obj[11]));
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<EventRequest> findEventRequestById(Long id) {
        logger.debug("Query start query find event request by id");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT eventRequest.ID_EVENT_REQUEST," +
                "       GUEST_ID," +
                "       STATUS," +
                "       CREATE_TIME," +
                "       MODIFIED_TIME," +
                "       PROVIDER_ID," +
                "       QUANTITY," +
                "       EVENT_ID," +
                "       TICKET_EVENT_ID," +
                "       QUANTITY_GEN," +
                "       USER_ID," +
                "       GUEST_CODE" +
                " FROM EVENT_REQUEST eventRequest" +
                " WHERE eventRequest.ID_EVENT_REQUEST = :id");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("id",id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            Object[] obj = result.get(0);
            EventRequest dto = new EventRequest();
            dto.setId(ValueUtil.getLongByObject(obj[0]));
            dto.setGuestId(ValueUtil.getLongByObject(obj[1]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
            dto.setCreateTime(ValueUtil.getTimestampByObject(obj[3]));
            dto.setModifiedTime(ValueUtil.getTimestampByObject(obj[4]));;
            dto.setProviderId(ValueUtil.getLongByObject(obj[5]));
            dto.setQuantity(ValueUtil.getLongByObject(obj[6]));
            dto.setEventId(ValueUtil.getLongByObject(obj[7]));
            dto.setTicketEventId(ValueUtil.getLongByObject(obj[8]));
            dto.setQuantityGen(ValueUtil.getIntegerByObject(obj[9]));
            dto.setUserId(ValueUtil.getLongByObject(obj[10]));
            dto.setGuestCode(ValueUtil.getStringByObject(obj[11]));
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void updateEventRequestById(Long id,Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE EVENT_REQUEST request" +
                " SET request.STATUS = :status " +
                " WHERE request.ID_EVENT_REQUEST = :id ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        query.setParameter("id",id);
        query.executeUpdate();
    }

}
