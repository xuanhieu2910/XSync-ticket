package compedia.vn.tickmi.mail.repository.impl;

import compedia.vn.tickmi.mail.dto.CustomerEmailDto;
import compedia.vn.tickmi.mail.dto.MailDto;
import compedia.vn.tickmi.mail.entity.MailInput;
import compedia.vn.tickmi.mail.utils.DbConstant;
import compedia.vn.tickmi.mail.utils.ValueUtil;
import compedia.vn.tickmi.mail.repository.MailInputRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MailInputRepositoryImpl implements MailInputRepositoryCustom {

    @Autowired
    EntityManager entityManager;


    @Override
    public List<MailDto> getMailDtosLitmit() throws IOException, SQLException {
        Query query = entityManager.createNativeQuery(SQL_getMailDtoLimit);
        query.setParameter("limit", DbConstant.SIZE_LIMIT);
        query.setParameter("retry", DbConstant.MAX_RETRY_DETAIL);
        List<Object[]> result = query.getResultList();
        List<MailDto> mailDtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(mailDtos)) {
            for (Object[] obj : result) {
                MailDto mailDto = new MailDto();
                mailDto.setId(ValueUtil.getIntegerByObject(obj[0]));
                mailDto.setObjectId(ValueUtil.getIntegerByObject(obj[1]));
                mailDto.setType(ValueUtil.getIntegerByObject(obj[2]));
                mailDto.setContent(ValueUtil.getClobString((Clob) obj[3]));
                mailDto.setRetry(ValueUtil.getIntegerByObject(obj[4]));
                mailDto.setProviderId(ValueUtil.getIntegerByObject(obj[5]));
                mailDto.setEmailCustomer(ValueUtil.getStringByObject(obj[6]));
                mailDto.setSubject(ValueUtil.getStringByObject(obj[7]));
                String emailUser = ValueUtil.getStringByObject(obj[8]);
                if (emailUser != null) {
                    CustomerEmailDto customerEmailDto = new CustomerEmailDto();
                    customerEmailDto.setUser(emailUser);
                    customerEmailDto.setPassword(ValueUtil.getStringByObject(obj[9]));
                    customerEmailDto.setHost(ValueUtil.getStringByObject(obj[10]));
                    customerEmailDto.setPort(ValueUtil.getStringByObject(obj[11]));
                    mailDto.setCustomerEmailDto(customerEmailDto);
                }

                mailDtos.add(mailDto);
            }
        }
        return mailDtos;
    }

    @Modifying
    @Transactional
    @Override
    public void updateMailInputRetryById(Integer id, Integer retry) {
        Query query = entityManager.createNativeQuery(SQL_updateMailInputRetryStatusById);
        query.setParameter("retry", retry);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Modifying
    @Transactional
    @Override
    public void updateMailInputStatusById(List<Integer> ids) {
        Query query = entityManager.createNativeQuery(SQL_updateMailInputStatusByIds);
        query.setParameter("ids",ids);
        query.executeUpdate();
    }


    @Modifying
    @Transactional
    @Override
    public void deleteMailInputById(Integer id) {
        Query query = entityManager.createNativeQuery(SQL_deleteMailInputById);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private static String SQL_deleteMailInputById = "DELETE MAIL_INPUT mailInput" +
            " WHERE mailInput.ID_MAIL_INPUT = :id";

    private static String SQL_updateMailInputRetryStatusById = " UPDATE MAIL_INPUT mailInput " +
            " SET mailInput.RETRY = :retry, mailInput.STATUS = -1 " +
            " WHERE mailInput.ID_MAIL_INPUT = :id";

    private static String SQL_updateMailInputStatusByIds = " UPDATE MAIL_INPUT mailInput " +
            " SET mailInput.STATUS = 1 " +
            " WHERE mailInput.ID_MAIL_INPUT in (:ids)";


    private static String SQL_getMailDtoLimit = "SELECT mailInput.ID_MAIL_INPUT," +
            "       mailInput.OBJECT_ID," +
            "       mailInput.TYPE," +
            "       mailInput.CONTENT," +
            "       mailInput.RETRY," +
            "       mailInput.PROVIDER_ID," +
            "       mailInput.EMAIL_CUSTOMER," +
            "       mailInput.SUBJECT," +
            "       configEmail.EMAIL_USER," +
            "       configEmail.EMAIL_PASSWORD," +
            "       configEmail.EMAIL_HOST," +
            "       configEmail.EMAIL_PORT," +
            "       mailInput.STATUS" +
            " FROM MAIL_INPUT mailInput" +
            "    left join (select *" +
            "    from CONFIG_EMAIL configEmail" +
            "    where configEmail.IS_USED = 1) configEmail" +
            " on mailInput.PROVIDER_ID = configEmail.PROVIDER_ID" +
            " WHERE ROWNUM <= :limit" +
            " AND (mailInput.RETRY <= :retry AND mailInput.STATUS = -1 )";
}
