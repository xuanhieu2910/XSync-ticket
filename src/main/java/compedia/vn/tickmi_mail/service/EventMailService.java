package compedia.vn.tickmi_mail.service;

import compedia.vn.tickmi_mail.entity.EventMail;
import compedia.vn.tickmi_mail.repository.EventMailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventMailService {

    private final static Logger logger = LoggerFactory.getLogger(EventMailService.class);



    @Autowired
    private EventMailRepository eventMailRepository;

    public void saveEventMails (List<EventMail> eventMailList) {
        logger.debug("Service Event mail save all event mail");
        eventMailRepository.saveAll(eventMailList);
    }

    public void saveEventMail (EventMail eventMail) {
        logger.debug("Service  Event Mail save");
        eventMailRepository.save(eventMail);
    }
}
