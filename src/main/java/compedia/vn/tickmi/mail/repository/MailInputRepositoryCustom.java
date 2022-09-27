package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.dto.MailDto;
import compedia.vn.tickmi.mail.entity.MailInput;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MailInputRepositoryCustom {

    List<MailDto> getMailDtosLitmit() throws IOException, SQLException;

    void updateMailInputRetryById(Integer id, Integer retry);

    void updateMailInputStatusById(List<Integer> ids);

    void deleteMailInputById(Integer id);
}
