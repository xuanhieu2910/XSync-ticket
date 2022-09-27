package compedia.vn.tickmi.mail.service;

import compedia.vn.tickmi.mail.entity.MailRequest;
import compedia.vn.tickmi.mail.repository.MailRequestRepository;
import compedia.vn.tickmi.mail.dto.InformationMailDto;
import compedia.vn.tickmi.mail.response.MailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class MailRequestService {

    private final static Logger logger = LoggerFactory.getLogger(MailRequestService.class);


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
}
