package compedia.vn.tickmi.mail.service;

import compedia.vn.tickmi.mail.entity.MailRequest;
import compedia.vn.tickmi.mail.repository.MailRequestRepository;
import compedia.vn.tickmi.mail.response.MailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@Service
public class MailRequestService {



    @Autowired
    MailRequestRepository mailRequestRepository;

    public void saveMailRoot(MailRequest mailRequest) {
        mailRequestRepository.save(mailRequest);
    }

    public List<MailResponse> findAllMailRoot() throws IOException, SQLException {
        return mailRequestRepository.findAllMailRoot();
    }
    public void updateStatusMailRequestsByIds(List<Integer> ids) {
        mailRequestRepository.updateStatusMailRequestByIds(ids);
    }

    public void deleteById(Integer id) {
        mailRequestRepository.deleteById(id);
    }
}
