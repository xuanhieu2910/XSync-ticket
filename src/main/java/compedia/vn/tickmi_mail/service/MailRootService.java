package compedia.vn.tickmi_mail.service;

import compedia.vn.tickmi_mail.dto.InformationMailDto;
import compedia.vn.tickmi_mail.entity.MailRoot;
import compedia.vn.tickmi_mail.repository.MailRootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class MailRootService {

    private final static Logger logger = LoggerFactory.getLogger(MailRootService.class);


    @Autowired
    MailRootRepository mailRootRepository;

    public void saveMailRoot (MailRoot mailRoot) {
        mailRootRepository.save(mailRoot);
    }

    public List<MailRoot> findAllMailRoot (Integer status, Integer retry, Integer limit) {
        logger.info("Service start find all mail root");
        return mailRootRepository.findAllMailRoot(status, retry, limit);
    }

    public List<MailRoot> updateMailRoots (List<MailRoot> roots) {
        logger.info("Service start update mail root");
        return mailRootRepository.saveAll(roots);
    }

    public MailRoot updateMailRoot (MailRoot mailRoot) {
        logger.info("Service start update mail root");
        return mailRootRepository.save(mailRoot);
    }

    public void deleteMailRoot (MailRoot root) {
        logger.info("Service start delete mail root");
        mailRootRepository.delete(root);
    }

    public Optional<InformationMailDto> getInformationMailDtoByGuestId ( Long guestId) throws IOException, SQLException {
        logger.info("Service start get information mail root by guest id : " + guestId);
        return mailRootRepository.getInformationMailDtos(guestId);
    }
}
