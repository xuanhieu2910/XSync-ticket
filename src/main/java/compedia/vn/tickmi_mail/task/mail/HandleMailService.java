package compedia.vn.tickmi_mail.task.mail;


import compedia.vn.tickmi_mail.dto.InformationMailDto;
import compedia.vn.tickmi_mail.entity.MailRoot;
import compedia.vn.tickmi_mail.service.MailRootService;
import compedia.vn.tickmi_mail.utils.DbConstant;
import compedia.vn.tickmi_mail.utils.TemplateEmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Component
@EnableScheduling
@EnableAsync
public class HandleMailService {

    private final static Logger logger = LoggerFactory.getLogger(HandleMailService.class);

    private static Queue<MailRoot> queueRootMail = new ArrayDeque<>();

    @Autowired
    MailRootService mailRootService;


    /**
     *  1. Process get root mail
     *
     * */
    @Async
    @Scheduled(fixedRate = 2000)
    public void processGetRootMail () {
        try {
            logger.info("========================== START PROCESS GET ROOT MAIL ============================");
            List<MailRoot> mailRootList = mailRootService.findAllMailRoot(DbConstant.MAIL_ROOT_NEW, DbConstant.MAX_RETRY, DbConstant.SIZE_LIMIT);
            if (!CollectionUtils.isEmpty(mailRootList)) {
                queueRootMail.addAll(mailRootList);
            } else {
                logger.info("Mail root is empty!");
            }
            logger.info("========================== END PROCESS GET ROOT MAIL ==============================");
        }catch (Exception e) {
            logger.error("Lỗi này ROOT MAIL =====================>" + e.getMessage());
        }
    }

    /**
     *
     * 2. Process generate content to send email
     *
     * */
    @Scheduled(fixedDelay = 1000)
    public void processGenerateContentToSendEmail () {
        logger.info("========================= START PROCESS GENERATE CONTENT ===============================");
        if (!queueRootMail.isEmpty()) {
            MailRoot mailRoot = queueRootMail.poll();
            // Get all information
            try {
                Optional<InformationMailDto> dto = mailRootService.getInformationMailDtoByGuestId(mailRoot.getGuestId());
                // success
                if (dto.isPresent()) {
                    String replaceTmp = TemplateEmailUtils.replaceTemplateEmail(dto.get().getPathQr());
                    String result = TemplateEmailUtils.replaceTag(dto.get().getHtml(),TemplateEmailUtils.TAG_IMAGE_TEMPLATE,replaceTmp);
                    logger.info("RESULT NÈ ============================= : " + result);
                }
            } catch (IOException e) {
                // false
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.info("========================= END PROCESS GENERATE CONTENT ===============================");
    }


    /**
     *
     * 3. Process send email
     * */
    public void processSendEmail () {
        logger.info("========================= START PROCESS SEND EMAIL ===============================");

        logger.info("========================= END PROCESS SEND EMAIL ===============================");
    }
}
