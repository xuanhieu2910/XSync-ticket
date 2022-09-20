package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.dto.MailDto;
import compedia.vn.tickmi.mail.entity.MailInput;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MailInputRepositoryCustom {
    Optional<MailInput> getMailInputById(Integer id) throws IOException, SQLException;

    List<MailInput> getMailInputLimit() throws IOException, SQLException;

    List<MailDto> getMailDtosLitmit();

    void updateMailInputRetryById(Integer id, Integer retry);

    void deleteMailInputById(Integer id);
}
