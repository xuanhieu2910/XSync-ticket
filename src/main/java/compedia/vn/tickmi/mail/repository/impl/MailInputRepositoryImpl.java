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
    public Optional<MailInput> getMailInputById(Integer id) throws IOException, SQLException {
        Query query = entityManager.createNativeQuery(SQL_findMailInputById);
        query.setParameter("id", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                MailInput mailInput = new MailInput();
                mailInput.setId(ValueUtil.getIntegerByObject(obj[0]));
                mailInput.setObjectId(ValueUtil.getIntegerByObject(obj[1]));
                mailInput.setType(ValueUtil.getIntegerByObject(obj[2]));
                mailInput.setContent(ValueUtil.getClobString((Clob) obj[3]));
                mailInput.setRetry(ValueUtil.getIntegerByObject(obj[4]));
                mailInput.setProviderId(ValueUtil.getIntegerByObject(obj[5]));
                mailInput.setEmailCustomer(ValueUtil.getStringByObject(obj[6]));
                mailInput.setSubject(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(mailInput);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<MailInput> getMailInputLimit() throws IOException, SQLException {
        Query query = entityManager.createNativeQuery(SQL_getMailInputLimit);
        query.setParameter("limit", DbConstant.SIZE_LIMIT);
        List<Object[]> result = query.getResultList();
        List<MailInput> mailInputs = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                MailInput mailInput = new MailInput();
                mailInput.setId(ValueUtil.getIntegerByObject(obj[0]));
                mailInput.setObjectId(ValueUtil.getIntegerByObject(obj[1]));
                mailInput.setType(ValueUtil.getIntegerByObject(obj[2]));
                mailInput.setContent(ValueUtil.getClobString((Clob) obj[3]));
                mailInput.setRetry(ValueUtil.getIntegerByObject(obj[4]));
                mailInput.setProviderId(ValueUtil.getIntegerByObject(obj[5]));
                mailInput.setEmailCustomer(ValueUtil.getStringByObject(obj[6]));
                mailInput.setSubject(ValueUtil.getStringByObject(obj[7]));
                mailInputs.add(mailInput);
            }
        }
        return mailInputs;
    }

    @Override
    public List<MailDto> getMailDtosLitmit() {
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
                mailDto.setContent(ValueUtil.getStringByObject(obj[3]));
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
        Query query = entityManager.createNativeQuery(SQL_updateMailInputRetryById);
        query.setParameter("retry", retry);
        query.setParameter("id", id);
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

    private static String SQL_updateMailInputRetryById = "UPDATE MAIL_INPUT mailInput" +
            " SET mailInput.RETRY = :retry" +
            " WHERE mailInput.ID_MAIL_INPUT = :id";

    private static String SQL_findMailInputById = "SELECT mailInput.*" +
            " FROM MAIL_INPUT mailInput" +
            " WHERE mailInput.ID_MAIL_INPUT = :id";

    private static String SQL_getMailInputLimit = "SELECT mailInput.ID_MAIL_INPUT, OBJECT_ID, " +
            "       TYPE, CONTENT, RETRY, PROVIDER_ID, " +
            "       EMAIL_CUSTOMER " +
            " FROM MAIL_INPUT mailInput" +
            " WHERE ROWNUM <= :limit";

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
            "       configEmail.EMAIL_PORT" +
            "FROM MAIL_INPUT mailInput" +
            "    left join (select *" +
            "    from CONFIG_EMAIL configEmail" +
            "    where configEmail.IS_USED = 1) configEmail" +
            " on mailInput.PROVIDER_ID = configEmail.PROVIDER_ID" +
            " WHERE ROWNUM <= :limit" +
            " AND mailInput.RETRY <= :retry";
}
