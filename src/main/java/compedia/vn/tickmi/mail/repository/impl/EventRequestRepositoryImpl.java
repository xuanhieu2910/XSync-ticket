package compedia.vn.tickmi.mail.repository.impl;

import compedia.vn.tickmi.mail.repository.EventRequestRepositoryCustom;
import compedia.vn.tickmi.mail.utils.ValueUtil;
import compedia.vn.tickmi.mail.entity.EventRequest;
import compedia.vn.tickmi.mail.utils.DbConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class EventRequestRepositoryImpl implements EventRequestRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<EventRequest> getEventRequestByStatus() {
        log.debug("Query start get event request");
        Query query = entityManager.createNativeQuery(SQL_getEventRequestByStatus);
        query.setParameter("limitRow", DbConstant.SIZE_LIMIT);
        List<Object[]> result = query.getResultList();
        List<EventRequest> response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                EventRequest dto = new EventRequest();
                dto.setId(ValueUtil.getIntegerByObject(obj[0]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[1]));
                dto.setQuantity(ValueUtil.getIntegerByObject(obj[2]));
                dto.setEventId(ValueUtil.getIntegerByObject(obj[3]));
                dto.setTicketEventId(ValueUtil.getIntegerByObject(obj[4]));
                dto.setObjectId(ValueUtil.getIntegerByObject(obj[5]));
                dto.setType(ValueUtil.getIntegerByObject(obj[6]));
                dto.setProviderId(ValueUtil.getIntegerByObject(obj[7]));
                dto.setTicketGeneration(ValueUtil.getIntegerByObject(obj[8]));
                dto.setNameGuest(ValueUtil.getStringByObject(obj[9]) == null ? null : "");
                dto.setPhoneGuest(ValueUtil.getStringByObject(obj[10]) == null ? null : "");
                dto.setEmailGuest(ValueUtil.getStringByObject(obj[11]) == null ? null : "");
                response.add(dto);
            }
        }
        return response;
    }

    @Override
    public Optional<EventRequest> findEventRequestByIdAndStatus(Integer id, Integer status) {
        log.debug("Query start query find event request by id");
        Query query = entityManager.createNativeQuery(SQL_findEventRequestByIdAndStatus);
        query.setParameter("id", id);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            Object[] obj = result.get(0);
            EventRequest dto = new EventRequest();
            dto.setId(ValueUtil.getIntegerByObject(obj[0]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[1]));
            dto.setQuantity(ValueUtil.getIntegerByObject(obj[2]));
            dto.setEventId(ValueUtil.getIntegerByObject(obj[3]));
            dto.setTicketEventId(ValueUtil.getIntegerByObject(obj[4]));
            dto.setObjectId(ValueUtil.getIntegerByObject(obj[5]));
            dto.setType(ValueUtil.getIntegerByObject(obj[6]));
            dto.setProviderId(ValueUtil.getIntegerByObject(obj[7]));
            dto.setTicketGeneration(ValueUtil.getIntegerByObject(obj[8]));
            dto.setNameGuest(ValueUtil.getStringByObject(obj[9]));
            dto.setPhoneGuest(ValueUtil.getStringByObject(obj[10]));
            dto.setEmailGuest(ValueUtil.getStringByObject(obj[11]));
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<EventRequest> findEventRequestById(Integer id) {
        log.debug("Query start query find event request by id");
        Query query = entityManager.createNativeQuery(SQL_findEventRequestById);
        query.setParameter("id", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            Object[] obj = result.get(0);
            EventRequest dto = new EventRequest();
            dto.setId(ValueUtil.getIntegerByObject(obj[0]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
            dto.setProviderId(ValueUtil.getIntegerByObject(obj[5]));
            dto.setQuantity(ValueUtil.getIntegerByObject(obj[6]));
            dto.setEventId(ValueUtil.getIntegerByObject(obj[7]));
            dto.setTicketEventId(ValueUtil.getIntegerByObject(obj[8]));
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void updateEventRequestById(Integer id, Integer status) {
        Query query = entityManager.createNativeQuery(SQL_updateEventRequestById);
        query.setParameter("status", status);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private static String SQL_getEventRequestByStatus = "select ID_EVENT_REQUEST," +
            "       STATUS," +
            "       QUANTITY," +
            "       EVENT_ID," +
            "       TICKET_EVENT_ID," +
            "       OBJECT_ID," +
            "       TYPE," +
            "       PROVIDER_ID," +
            "       TICKET_GEN," +
            "       NAME_GUEST," +
            "       PHONE_GUEST," +
            "       EMAIL_GUEST" +
            " from EVENT_REQUEST evenRequest" +
            " where evenRequest.STATUS = -1" +
            "  and ROWNUM < :limitRow";

    private static String SQL_findEventRequestByIdAndStatus = "SELECT eventRequest.ID_EVENT_REQUEST,STATUS,QUANTITY," +
            "       EVENT_ID,TICKET_EVENT_ID,OBJECT_ID," +
            "       TYPE,PROVIDER_ID,TICKET_GEN,NAME_GUEST," +
            "       PHONE_GUEST,EMAIL_GUEST" +
            " FROM EVENT_REQUEST eventRequest" +
            " WHERE eventRequest.ID_EVENT_REQUEST = :id" +
            "  AND eventRequest.STATUS = :status";

    private static String SQL_findEventRequestById = "SELECT eventRequest.ID_EVENT_REQUEST, +" +
            "  GUEST_ID, +" +
            "  STATUS, +" +
            "  CREATE_TIME, +" +
            "  MODIFIED_TIME, +" +
            "  PROVIDER_ID, +" +
            "  QUANTITY, +" +
            "  EVENT_ID, +" +
            "  TICKET_EVENT_ID, +" +
            "  QUANTITY_GEN, +" +
            "  USER_ID, +" +
            "  GUEST_CODE +" +
            "    FROM EVENT_REQUEST eventRequest +" +
            "    WHERE eventRequest.ID_EVENT_REQUEST = :id";

    private static String SQL_updateEventRequestById = "UPDATE EVENT_REQUEST request +" +
            "    SET request.STATUS = :status  +" +
            "    WHERE request.ID_EVENT_REQUEST = :id ";

}
