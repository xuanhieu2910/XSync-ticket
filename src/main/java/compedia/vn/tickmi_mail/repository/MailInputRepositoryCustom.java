package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.MailInput;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface MailInputRepositoryCustom {
    Optional<MailInput> getMailInputById(Integer id) throws IOException, SQLException;
}
