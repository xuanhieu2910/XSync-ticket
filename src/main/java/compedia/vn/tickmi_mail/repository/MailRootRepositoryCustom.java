package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.dto.InformationMailDto;
import compedia.vn.tickmi_mail.entity.MailRoot;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MailRootRepositoryCustom {

    List<MailRoot> findAllMailRoot (Integer status, Integer retry , Integer limit);

    Optional<InformationMailDto> getInformationMailDtos (Long guestId) throws IOException, SQLException;
}
