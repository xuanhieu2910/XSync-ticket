package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.response.MailResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface MailRequestRepositoryCustom {

    List<MailResponse> findAllMailRoot() throws IOException, SQLException;


    void updateStatusMailRequestByIds(List<Integer> ids);
}
