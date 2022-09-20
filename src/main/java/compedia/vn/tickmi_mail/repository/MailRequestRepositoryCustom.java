package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.dto.InformationMailDto;
import compedia.vn.tickmi_mail.entity.MailRequest;
import compedia.vn.tickmi_mail.response.mail.MailResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MailRequestRepositoryCustom {

    List<MailResponse> findAllMailRoot () throws IOException, SQLException;

    Optional<InformationMailDto> getInformationMailDtos (Integer guestId) throws IOException, SQLException;

    void updateStatusMailRequestByIds (List<Integer>ids);
}
