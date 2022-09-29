package compedia.vn.tickmi.mail.task.mail;

import compedia.vn.tickmi.mail.entity.MailInput;
import compedia.vn.tickmi.mail.repository.MailInputRepository;
import compedia.vn.tickmi.mail.response.MailResponse;
import compedia.vn.tickmi.mail.service.MailDetailHisService;
import compedia.vn.tickmi.mail.service.MailRequestService;
import compedia.vn.tickmi.mail.utils.DbConstant;
import compedia.vn.tickmi.mail.utils.TemplateEmailUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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

    private Queue<MailResponse> mailResponsesQueue = new ArrayDeque<>();

    private static String url;
    @Autowired
    public HandleMailRequest (@Value("${vnp.cpa.url}") String url) {
        this.url = url;
    }

    @Async
    @Scheduled(fixedRate = 2000)
    public void getDataMailRequest() {
        try {
            List<MailResponse> mailResponses = mailRequestService.findAllMailRoot();
            if (!CollectionUtils.isEmpty(mailResponses)) {
                List<Integer> ids = new ArrayList<>();
                for (MailResponse dto : mailResponses) {
                    ids.add(dto.getId());
                }
                mailRequestService.updateStatusMailRequestsByIds(ids);
                mailResponsesQueue.addAll(mailResponses);
                log.info("Save to queue mail response success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Async
    @Scheduled(fixedRate = 500)
    public  void pushDataToMailInput () {
        while (!mailResponsesQueue.isEmpty()) {
            MailResponse mailResponse = mailResponsesQueue.poll();
            MailInput input = new MailInput();
            input.setObjectId(mailResponse.getObjectId());
            input.setType(mailResponse.getType());
            input.setContent(TemplateEmailUtils.replaceTemplateTicket(mailResponse.getContent(),mailResponse.getHtmlReplace(),mailResponse.getPathQr(),url));
            log.info("CONTENT SEND MAIL: " + mailResponse.getContent());
            input.setRetry(mailResponse.getRetry());
            input.setProviderId(mailResponse.getProviderId());
            input.setEmailCustomer(mailResponse.getEmailGuest());
            input.setStatus(DbConstant.MAIL_ROOT_STATUS_NEW);
            input.setSubject("VÉ SỰ KIỆN : " + mailResponse.getEventName());
            input.setTicketEventId(mailResponse.getTicketEventId());
            input.setEventId(mailResponse.getEventId());
            input.setNameGuest(mailResponse.getNameGuest());
            input.setPhoneGuest(mailResponse.getPhoneGuest());
            input.setQuantity(mailResponse.getQuantity());
            mailInputRepository.save(input);
            log.info("Save to mail input with :" + input.toString());
        }
    }

}
