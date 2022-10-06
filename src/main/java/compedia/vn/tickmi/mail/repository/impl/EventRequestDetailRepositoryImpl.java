package compedia.vn.tickmi.mail.repository.impl;

import compedia.vn.tickmi.mail.entity.EventRequestDetail;
import compedia.vn.tickmi.mail.repository.EventRequestRepositoryDetailCustom;
import compedia.vn.tickmi.mail.utils.DbConstant;
import compedia.vn.tickmi.mail.utils.ValueUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.Entity;
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
    public List<EventRequestDetail> getAllEventRequestDetailCustom() {
        log.debug("Start query find event request detail");
        Query query = entityManager.createNativeQuery(SQL_GetAllEventRequestDetailCustom);
        query.setParameter("limit", DbConstant.SIZE_LIMIT);
        List<Object[]> result = query.getResultList();
        List<EventRequestDetail> response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                EventRequestDetail detail = new EventRequestDetail();
                detail.setId(ValueUtil.getIntegerByObject(obj[0]));
                detail.setIndexTicket(ValueUtil.getIntegerByObject(obj[1]));
                detail.setCodeTicket(ValueUtil.getStringByObject(obj[2]));
                detail.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                detail.setRetry(ValueUtil.getIntegerByObject(obj[4]));
                detail.setEventId(ValueUtil.getIntegerByObject(obj[5]));
                detail.setTicketEventId(ValueUtil.getIntegerByObject(obj[6]));
                detail.setProviderId(ValueUtil.getIntegerByObject(obj[7]));
                detail.setObjectId(ValueUtil.getIntegerByObject(obj[8]));
                detail.setType(ValueUtil.getIntegerByObject(obj[9]));
                detail.setNameGuest(ValueUtil.getStringByObject(obj[10]));
                detail.setPhoneGuest(ValueUtil.getStringByObject(obj[11]));
                detail.setEmailGuest(ValueUtil.getStringByObject(obj[12]));
                detail.setEventRequestId(ValueUtil.getIntegerByObject(obj[13]));
                response.add(detail);
            }
        }
        return response;
    }


    @Transactional
    @Modifying
    @Override
    public void deleteByIdCustom(Integer id) {
        Query query = entityManager.createNativeQuery(SQL_DeleteEventRequestDetailById);
        query.setParameter("id",id);
        query.executeUpdate();
    }


    private static String SQL_DeleteEventRequestDetailById = " DELETE EVENT_REQUEST_DETAILS detail WHERE detail.ID_REQUEST_DETAILS = :id ";


    private static String SQL_GetAllEventRequestDetailCustom = "SELECT ID_REQUEST_DETAILS,INDEX_TICKET," +
            "       CODE_TICKET,STATUS,RETRY," +
            "       EVENT_ID,TICKET_EVENT_ID,PROVIDER_ID," +
            "       OBJECT_ID,TYPE,NAME_GUEST,PHONE_GUEST," +
            "       EMAIL_GUEST,EVENT_REQUEST_ID" +
            " FROM EVENT_REQUEST_DETAILS details" +
            " WHERE details.STATUS = -1 " +
            "  AND ROWNUM < :limit ";


}
