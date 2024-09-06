package compedia.vn.tickmi.mail.repository.impl;

import compedia.vn.tickmi.mail.entity.EventRequestDetail;
import compedia.vn.tickmi.mail.repository.EventRequestRepositoryDetailCustom;
import compedia.vn.tickmi.mail.utils.DbConstant;
import compedia.vn.tickmi.mail.utils.ValueUtil;
import lombok.Value;
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
                detail.setId(ValueUtil.getLongByObject(obj[0]));
                detail.setIndexTicket(ValueUtil.getIntegerByObject(obj[1]));
                detail.setCodeTicket(ValueUtil.getStringByObject(obj[2]));
                detail.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                detail.setRetry(ValueUtil.getIntegerByObject(obj[4]));
                detail.setEventId(ValueUtil.getLongByObject(obj[5]));
                detail.setTicketEventId(ValueUtil.getLongByObject(obj[6]));
                detail.setProviderId(ValueUtil.getIntegerByObject(obj[7]));
                detail.setObjectId(ValueUtil.getLongByObject(obj[8]));
                detail.setType(ValueUtil.getIntegerByObject(obj[9]));
                detail.setNameGuest(ValueUtil.getStringByObject(obj[10]));
                detail.setPhoneGuest(ValueUtil.getStringByObject(obj[11]));
                detail.setEmailGuest(ValueUtil.getStringByObject(obj[12]));
                detail.setEventRequestId(ValueUtil.getLongByObject(obj[13]));
                detail.setPathLogo(ValueUtil.getStringByObject(obj[14]));
                detail.setIsDisplayName(ValueUtil.getIntegerByObject(obj[15]));
                detail.setIsDisplayLogo(ValueUtil.getIntegerByObject(obj[16]));
                detail.setNote(ValueUtil.getStringByObject(obj[17]));
                detail.setAvatarPath(ValueUtil.getStringByObject(obj[18]));
                detail.setIsPackageFree(ValueUtil.getIntegerByObject(obj[19]));
                detail.setLimitScanner(ValueUtil.getIntegerByObject(obj[20]));
                detail.setQrContentPrefix(ValueUtil.getStringByObject(obj[21]));
                response.add(detail);
            }
        }
        return response;
    }


    @Transactional
    @Modifying
    @Override
    public void deleteByIdCustom(Long id) {
        Query query = entityManager.createNativeQuery(SQL_DeleteEventRequestDetailById);
        query.setParameter("id",id);
        query.executeUpdate();
    }


    @Transactional
    @Override
    public void updateWholeEventRequestDetail() {
        Query query = entityManager.createNativeQuery(updateEventRequestDetail);
        query.executeUpdate();
    }

    private static String updateEventRequestDetail = " UPDATE EVENT_REQUEST_DETAILS eventRequestDetails " +
            "SET eventRequestDetails.STATUS = -1 " +
            " WHERE 1 = 1 AND eventRequestDetails.STATUS = 1 ";

    private static String SQL_DeleteEventRequestDetailById = " DELETE EVENT_REQUEST_DETAILS detail WHERE detail.ID_REQUEST_DETAILS = :id ";


    private static String SQL_GetAllEventRequestDetailCustom = "SELECT ID_REQUEST_DETAILS, " +
            "       INDEX_TICKET, " +
            "       CODE_TICKET, " +
            "       STATUS, " +
            "       RETRY, " +
            "       EVENT_ID, " +
            "       TICKET_EVENT_ID, " +
            "       PROVIDER_ID, " +
            "       OBJECT_ID, " +
            "       TYPE, " +
            "       NAME_GUEST, " +
            "       PHONE_GUEST, " +
            "       EMAIL_GUEST, " +
            "       EVENT_REQUEST_ID, " +
            "       PATH_LOGO, " +
            "       IS_DISPLAY_NAME, " +
            "       IS_DISPLAY_LOGO, " +
            "       NOTE, " +
            "       AVATAR_PATH, " +
            "       IS_PACKAGE_FREE, " +
            "       LIMIT_SCANNER, " +
            "       QR_CONTENT_PREFIX " +
            "FROM EVENT_REQUEST_DETAILS details " +
            "WHERE details.STATUS = -1 " +
            "  AND ROWNUM < :limit " +
            "ORDER BY OBJECT_ID ";


}
