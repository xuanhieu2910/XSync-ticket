package compedia.vn.tickmi.mail.repository.impl;

import compedia.vn.tickmi.mail.repository.MailRequestRepositoryCustom;
import compedia.vn.tickmi.mail.response.MailResponse;
import compedia.vn.tickmi.mail.dto.InformationMailDto;
import compedia.vn.tickmi.mail.utils.DbConstant;
import compedia.vn.tickmi.mail.utils.ValueUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class MailRequestRepositoryImpl implements MailRequestRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<MailResponse> findAllMailRoot() throws IOException, SQLException {
        Query query = entityManager.createNativeQuery(SQL_findAllMailRequest);
        query.setParameter("retry", DbConstant.INIT_RETRY);
        query.setParameter("limit", DbConstant.SIZE_LIMIT);
        List<Object[]> result = query.getResultList();
        List<MailResponse> response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                Integer isCheck = ValueUtil.getIntegerByObject(obj[12]);
                if (isCheck.equals(1)) {
                    MailResponse dto = new MailResponse();
                    dto.setId(ValueUtil.getIntegerByObject(obj[0]));
                    dto.setEventId(ValueUtil.getIntegerByObject(obj[1]));
                    dto.setTicketEventId(ValueUtil.getIntegerByObject(obj[2]));
                    dto.setObjectId(ValueUtil.getIntegerByObject(obj[3]));
                    dto.setType(ValueUtil.getIntegerByObject(obj[4]));
                    dto.setProviderId(ValueUtil.getIntegerByObject(obj[5]));
                    dto.setPathQr(ValueUtil.getStringByObject(obj[6]));
                    dto.setContent(ValueUtil.getClobString((Clob) obj[7]) == null ? null : ValueUtil.getClobString((Clob) obj[7]));
                    dto.setQuantity(ValueUtil.getIntegerByObject(obj[8]));
                    dto.setNameGuest(ValueUtil.getStringByObject(obj[9]));
                    dto.setPhoneGuest(ValueUtil.getStringByObject(obj[10]));
                    dto.setEmailGuest(ValueUtil.getStringByObject(obj[11]));
                    dto.setHtmlReplace(ValueUtil.getClobString((Clob) obj[13]) == null ? null : ValueUtil.getClobString((Clob) obj[13]));
                    dto.setRetry(ValueUtil.getIntegerByObject(obj[14]));
                    dto.setEmailFrom(ValueUtil.getStringByObject(obj[15]));
                    dto.setPassword(ValueUtil.getStringByObject(obj[16]));
                    dto.setEmailHost(ValueUtil.getStringByObject(obj[17]));
                    dto.setEmailPort(ValueUtil.getStringByObject(obj[18]));
                    response.add(dto);
                }
            }
        }
        return response;
    }


    @Transactional
    @Modifying
    @Override
    public void updateStatusMailRequestByIds(List<Integer> ids) {
        Query query = entityManager.createNativeQuery(SQL_updateStatusMailRequestByIds);
        query.setParameter("status", DbConstant.MAIL_HIS_STATUS_SUCCESS);
        query.setParameter("ids", ids);
        query.executeUpdate();
    }


    private static String SQL_findAllMailRequest = "WITH ROOT as ( " +
            "    SELECT mailRequest.EVENT_ID,mailRequest.TICKET_EVENT_ID,mailRequest.OBJECT_ID,mailRequest.TYPE, " +
            "           mailRequest.PROVIDER_ID,mailRequest.ID,mailRequest.QUANTITY, mailRequest.NAME_GUEST, " +
            "           mailRequest.PHONE_GUEST,mailRequest.EMAIL_GUEST, mailRequest.RETRY " +
            "    FROM MAIL_REQUEST mailRequest " +
            "    WHERE mailRequest.STATUS = -1 " +
            "      AND mailRequest.RETRY <= :retry " +
            "      AND ROWNUM <= :limit " +
            "), " +
            "     ROOT_DETAIL AS ( " +
            "         select root.ID,root.EVENT_ID,root.TICKET_EVENT_ID, root.OBJECT_ID,root.TYPE, " +
            "                root.PROVIDER_ID, " +
            "                LISTAGG(ticket.PATH_QR, ';') WITHIN GROUP (ORDER BY ticket.INDEX_QR) pathQR, " +
            "                count(ticket.TICKET_ID) countTicketId,root.QUANTITY,root.NAME_GUEST, " +
            "                root.PHONE_GUEST,root.EMAIL_GUEST,root.RETRY " +
            "         from ROOT root " +
            "                  inner join TICKET ticket on root.OBJECT_ID = ticket.OBJECT_ID " +
            "             and root.TYPE = ticket.TYPE " +
            "         group by root.EVENT_ID, root.TICKET_EVENT_ID, root.OBJECT_ID, root.TYPE, " +
            "                  root.PROVIDER_ID, root.ID, root.QUANTITY, root.NAME_GUEST, root.PHONE_GUEST, " +
            "                  root.EMAIL_GUEST, root.RETRY " +
            "     ), " +
            "     ROOT_RESULT as ( " +
            "         select rootDetail.ID, rootDetail.EVENT_ID,rootDetail.TICKET_EVENT_ID,rootDetail.OBJECT_ID, " +
            "                rootDetail.TYPE,rootDetail.PROVIDER_ID,pathQR,templateTicket.HTML,rootDetail.QUANTITY, " +
            "                rootDetail.NAME_GUEST,rootDetail.PHONE_GUEST,rootDetail.EMAIL_GUEST, " +
            "                case when rootDetail.QUANTITY < countTicketId then 0 else 1 end ticketGen, " +
            "                templateTicket.HTML_REPLACE,rootDetail.RETRY,configEmail.EMAIL_USER mailFrom, " +
            "                configEmail.EMAIL_PASSWORD password,configEmail.EMAIL_HOST  emailHost,configEmail.EMAIL_PORT  emailPort " +
            "         from ROOT_DETAIL rootDetail " +
            "                  inner join TICKET_EVENT ticketEvent on rootDetail.TICKET_EVENT_ID = ticketEvent.TICKET_EVENT_ID " +
            "             and rootDetail.EVENT_ID = ticketEvent.EVENT_ID " +
            "                  inner join TEMPLATE_TICKET templateTicket " +
            "                             on ticketEvent.TEMPLATE_TICKET_ID = templateTicket.TEMPLATE_TICKET_ID " +
            "                  left join (select * from CONFIG_EMAIL configEmail where configEmail.IS_USED = 1) configEmail " +
            "                            on rootDetail.PROVIDER_ID = configEmail.PROVIDER_ID " +
            "     ) " +
            "select * " +
            "from ROOT_RESULT";

    private static String SQL_updateStatusMailRequestByIds = "UPDATE MAIL_REQUEST mailRequest " +
            " SET mailRequest.STATUS = :status" +
            " WHERE mailRequest.ID in (:ids)";
}
