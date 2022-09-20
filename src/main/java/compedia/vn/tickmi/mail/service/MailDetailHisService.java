package compedia.vn.tickmi.mail.service;

import compedia.vn.tickmi.mail.entity.MailDetailHis;
import compedia.vn.tickmi.mail.repository.MailDetailHisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailDetailHisService {

    private final static Logger logger = LoggerFactory.getLogger(MailDetailHisService.class);


    @Autowired
    MailDetailHisRepository mailDetailHisRepository;


    public MailDetailHis updateMailDetailHis(MailDetailHis mailDetailHis) {
        return mailDetailHisRepository.save(mailDetailHis);
    }
}
