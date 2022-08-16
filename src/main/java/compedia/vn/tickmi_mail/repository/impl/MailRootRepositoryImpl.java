package compedia.vn.tickmi_mail.repository.impl;

import compedia.vn.tickmi_mail.dto.InformationMailDto;
import compedia.vn.tickmi_mail.entity.MailRoot;
import compedia.vn.tickmi_mail.repository.MailRootRepositoryCustom;
import compedia.vn.tickmi_mail.utils.ValueUtil;
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

public class MailRootRepositoryImpl implements MailRootRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<MailRoot> findAllMailRoot(Integer status, Integer retry, Integer limit) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT root.*" +
                " FROM MAIL_ROOT root" +
                " WHERE root.STATUS = :status" +
                "  AND root.RETRY <= :retry" +
                "  AND ROWNUM <= :limit");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        query.setParameter("retry",retry);
        query.setParameter("limit", limit);
        List<Object[]> result = query.getResultList();
        List<MailRoot> response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                MailRoot mailRoot = new MailRoot();
                mailRoot.setId(ValueUtil.getLongByObject(obj[0]));
                mailRoot.setGuestId(ValueUtil.getLongByObject(obj[1]));
                mailRoot.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                mailRoot.setRetry(ValueUtil.getIntegerByObject(obj[3]));
                mailRoot.setCreateTime(ValueUtil.getTimestampByObject(obj[4]));
                mailRoot.setModifiedTime(ValueUtil.getTimestampByObject(obj[5]));
                mailRoot.setProviderId(ValueUtil.getLongByObject(obj[6]));
                response.add(mailRoot);
            }
        }
        return response;
    }

    @Override
    public Optional<InformationMailDto> getInformationMailDtos(Long guestId) throws IOException, SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT case when configMail.EMAIL_USER is null then null else configMail.EMAIL_USER end         EMAIL_USER," +
                "       case when configMail.EMAIL_PASSWORD is null then null else configMail.EMAIL_PASSWORD end EMAIL_PASSWORD," +
                "       case when configMail.EMAIL_HOST is null then null else configMail.EMAIL_HOST end         EMAIL_HOST," +
                "       case when configMail.EMAIL_PORT is null then null else configMail.EMAIL_PORT end         EMAIL_PORT," +
                "       ticketEvent.HTML," +
                "       ticket.PATH_QR," +
                "       guest.GUEST_ID," +
                "       ticket.INDEX_QR" +
                " FROM MAIL_ROOT mailRoot" +
                "         inner join GUEST guest on mailRoot.GUEST_ID = guest.GUEST_ID" +
                "         inner join TICKET_EVENT ticketEvent on guest.TICKET_EVENT_ID = ticketEvent.TICKET_EVENT_ID" +
                "         inner join TICKET ticket" +
                "                    on guest.GUEST_ID = ticket.GUEST_ID and ticketEvent.TICKET_EVENT_ID = ticket.TICKET_EVENT_ID" +
                "         left join (select configMail.*" +
                "                    from CONFIG_EMAIL configMail" +
                "                    where configMail.IS_USED = 1) configMail" +
                "                   on mailRoot.PROVIDER_ID = configMail.PROVIDER_ID" +
                " where mailRoot.GUEST_ID = :guestId" +
                "  and guest.STATUS = 0" +
                "  and ticket.STATUS = 0" +
                " ORDER BY ticket.INDEX_QR");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("guestId", guestId);
        List<Object[]> result = query.getResultList();
        InformationMailDto informationMailDto = new InformationMailDto();
        if (!CollectionUtils.isEmpty(result)) {
            // Commons
            Object[] common = result.get(0);
            informationMailDto.setEmailUser(ValueUtil.getStringByObject(common[0]) == null ? null :ValueUtil.getStringByObject(common[0]));
            informationMailDto.setEmailPassword(ValueUtil.getStringByObject(common[1]) == null ? null : ValueUtil.getStringByObject(common[1]));
            informationMailDto.setEmailHost(ValueUtil.getStringByObject(common[2]) == null ? null : ValueUtil.getStringByObject(common[2]) );
            informationMailDto.setEmailPort(ValueUtil.getStringByObject(common[3]) == null ? null : ValueUtil.getStringByObject(common[3]));
            informationMailDto.setHtml(ValueUtil.getClobString((Clob) common[4]));
            informationMailDto.setGuestId(ValueUtil.getLongByObject(common[6]));
            // Detail
            List<String> pathQr = new ArrayList<>();
            for (Object[] obj : result) {
                pathQr.add(ValueUtil.getStringByObject(obj[5]));
            }
            informationMailDto.setPathQr(pathQr);
            return Optional.of(informationMailDto);
        }
        return Optional.empty();
    }


}
