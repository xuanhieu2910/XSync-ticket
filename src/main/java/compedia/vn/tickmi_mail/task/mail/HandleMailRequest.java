package compedia.vn.tickmi_mail.task.mail;

import compedia.vn.tickmi_mail.dto.InformationMailDto;
import compedia.vn.tickmi_mail.entity.MailDetailHis;
import compedia.vn.tickmi_mail.response.mail.MailResponse;
import compedia.vn.tickmi_mail.service.MailDetailHisService;
import compedia.vn.tickmi_mail.service.MailRequestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;

@Log4j2
@Component
@EnableScheduling
@EnableAsync
public class HandleMailRequest{


    @Autowired
    MailRequestService mailRequestService;
    @Autowired
    MailDetailHisService detailHisService;


    @Async
    @Scheduled(fixedRate = 1000)
    public void getDataMailRequest () {
        log.info("Start to get data mail response");
        try {
            List<MailResponse> mailResponses = mailRequestService.findAllMailRoot();
            if (!CollectionUtils.isEmpty(mailResponses)) {
                // update status
                List<Integer>ids = new ArrayList<>();
                for (MailResponse dto : mailResponses) {
                    ids.add(dto.getId());
                }
                mailRequestService.updateStatusMailRequestsByIds(ids);
                // Save to mail input
            }
            else {
                log.info("Data in mail request empty!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
