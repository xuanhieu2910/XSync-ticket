package compedia.vn.tickmi_mail.repository.impl;

import compedia.vn.tickmi_mail.entity.MailInput;
import compedia.vn.tickmi_mail.repository.MailInputRepositoryCustom;
import compedia.vn.tickmi_mail.utils.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MailInputRepositoryImpl implements MailInputRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public Optional<MailInput> getMailInputById(Integer id) throws IOException, SQLException {
        Query query = entityManager.createNativeQuery(SQL_findMailInputById);
        query.setParameter("id",id);
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

    private static String SQL_findMailInputById = "SELECT mailInput.*" +
            " FROM MAIL_INPUT mailInput" +
            " WHERE mailInput.ID_MAIL_INPUT = :id";
}
