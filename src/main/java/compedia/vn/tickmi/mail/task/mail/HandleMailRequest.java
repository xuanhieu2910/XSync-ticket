package compedia.vn.tickmi.mail.task.mail;

import compedia.vn.tickmi.mail.entity.MailInput;
import compedia.vn.tickmi.mail.repository.MailInputRepository;
import compedia.vn.tickmi.mail.response.MailResponse;
import compedia.vn.tickmi.mail.service.MailDetailHisService;
import compedia.vn.tickmi.mail.service.MailRequestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
@EnableScheduling
@EnableAsync
public class HandleMailRequest {

    @Autowired
    MailRequestService mailRequestService;

    @Autowired
    MailDetailHisService detailHisService;

    @Autowired
    MailInputRepository mailInputRepository;

    @Async
    @Scheduled(fixedRate = 1000)
    public void getDataMailRequest() {
        log.info("Start to get data mail response");
        try {
            List<MailResponse> mailResponses = mailRequestService.findAllMailRoot();
            if (!CollectionUtils.isEmpty(mailResponses)) {
                // Update status
                List<Integer> ids = new ArrayList<>();
                List<MailInput> mailInputs = new ArrayList<>();
                for (MailResponse dto : mailResponses) {
                    ids.add(dto.getId());
                    MailInput input = new MailInput();
                    input.setObjectId(dto.getObjectId());
                    input.setType(dto.getType());
                    input.setContent(dto.getContent());
                    input.setRetry(dto.getRetry());
                    input.setProviderId(dto.getProviderId());
                    input.setEmailCustomer(dto.getEmailGuest());
                    mailInputs.add(input);
                }
                mailRequestService.updateStatusMailRequestsByIds(ids);
                mailInputRepository.saveAll(mailInputs);
            } else {
                log.info("Data in mail request empty!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
