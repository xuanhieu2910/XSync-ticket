package compedia.vn.tickmi_mail.service;

import compedia.vn.tickmi_mail.entity.MailRoot;
import compedia.vn.tickmi_mail.repository.MailRootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailRootService {

    private final static Logger logger = LoggerFactory.getLogger(MailRootService.class);


    @Autowired
    MailRootRepository mailRootRepository;

    public void saveMailRoot (MailRoot mailRoot) {
        mailRootRepository.save(mailRoot);
    }
}
