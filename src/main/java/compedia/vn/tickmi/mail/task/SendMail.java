package compedia.vn.tickmi.mail.task;

import compedia.vn.tickmi.mail.dto.MailDto;
import compedia.vn.tickmi.mail.dto.SmtpAuthenticator;
import compedia.vn.tickmi.mail.entity.MailDetailHis;
import compedia.vn.tickmi.mail.repository.MailDetailHisRepository;
import compedia.vn.tickmi.mail.repository.MailInputRepository;
import compedia.vn.tickmi.mail.utils.DbConstant;
import compedia.vn.tickmi.mail.utils.PropertiesUtil;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.*;

@Log4j2
@Component
@EnableScheduling
@EnableAsync
@Configuration
@PropertySource("classpath:/email.properties")
public class SendMail{

    @Autowired
    MailInputRepository mailInputRepository;

    @Autowired
    MailDetailHisRepository mailDetailHisRepository;

    private static String emailFrom;


    @Autowired
    public SendMail (@Value("${mail.user}") String emailFrom) {
        this.emailFrom = emailFrom;
    }


    private static Queue<MailDto> mailDtoQueue = new ArrayDeque<>();

    @Async
    @Scheduled(fixedRate = 4000)
    public void getEmailTo() {
        log.info("Start to get data mail response");
        try {
            log.info("==============================" + emailFrom + "=============================");
            List<MailDto> mailDtos = mailInputRepository.getMailDtosLitmit();
            if (!CollectionUtils.isEmpty(mailDtos)) {
                List<Integer> ids = new ArrayList<>();
                for (MailDto dto : mailDtos) {
                    ids.add(dto.getId());
                }
                // update status
                mailInputRepository.updateMailInputStatusById(ids);
                mailDtoQueue.addAll(mailDtos);
            }
            log.info("DATA MAIL INPUT EMPTY!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }



    @Synchronized
    private boolean send(MailDto mailDto) {
        try {
            Properties emailProps = new Properties();
            if (mailDto.getCustomerEmailDto() != null) {
                emailProps.put("mail.user", mailDto.getCustomerEmailDto().getUser());
                emailProps.put("mail.password", mailDto.getCustomerEmailDto().getPassword());
                emailProps.put("mail.smtp.host", mailDto.getCustomerEmailDto().getHost());
                emailProps.put("mail.smtp.port", mailDto.getCustomerEmailDto().getPort());
                emailProps.put("mail.smtp.connectiontimeout", mailDto.getCustomerEmailDto().getConnectionTimeOut());
                emailProps.put("mail.smtp.timeout", mailDto.getCustomerEmailDto().getTimeout());
                emailProps.put("mail.smtp.auth", mailDto.getCustomerEmailDto().getAuth());
                emailProps.put("mail.smtp.ssl.enable", mailDto.getCustomerEmailDto().getSslEnable());
                emailProps.put("mail.smtp.ssl.trust", mailDto.getCustomerEmailDto().getHost());
            } else {
                emailProps.load(PropertiesUtil.class.getResourceAsStream("/email.properties"));
            }
            SmtpAuthenticator smtpAuthenticator = new SmtpAuthenticator(emailProps.getProperty("mail.user"), emailProps.getProperty("mail.password"));

            // Get the default Session object.
            Session session = Session.getDefaultInstance(emailProps, smtpAuthenticator);

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(emailProps.getProperty("mail.user")));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDto.getEmailCustomer()));
            // Set Subject: header field
            message.setSubject(mailDto.getSubject(), "UTF-8");

            // Send the actual HTML message, as big as you like
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            // message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setContent(mailDto.getContent(), "text/html; charset=UTF-8");
            // Send message
            Transport.send(message);

            log.info("Send mail to: " + mailDto.getEmailCustomer() + " - with content: " + message.getContent());

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    @Async
    @Scheduled(fixedRate = 1000)
    public void run() {
        while (!mailDtoQueue.isEmpty()) {
            MailDto mailDto = mailDtoQueue.poll();
            if (!send(mailDto)) {
                int retry = mailDto.getRetry();
                if (retry == DbConstant.MAX_RETRY) {
                    // Insert mail his
                    mailDetailHisRepository.save(createMailDetailHis(mailDto));
                    log.info("Save mail detail his: " + mailDto.toString());

                    // Delete mail input
                    mailInputRepository.deleteMailInputById(mailDto.getId());
                    log.info("Delete mail input with id: " + mailDto.getId());
                } else {
                    ++retry;
                    mailInputRepository.updateMailInputRetryById(mailDto.getId(), retry);
                    log.info("Update mail input with id: " + mailDto.getId() + " - retry: " + retry);
                }
            } else {
                mailInputRepository.deleteMailInputById(mailDto.getId());
                mailDetailHisRepository.save(createMailDetailHis(mailDto));
                log.info("Delete mail input with id: " + mailDto.getId());
            }
        }
        log.info("Queue email dto empty!");
    }

    private MailDetailHis createMailDetailHis(MailDto mailDto) {
        MailDetailHis mailDetailHis = new MailDetailHis();
        if (mailDto.getCustomerEmailDto() != null) {
            mailDetailHis.setMailFrom(mailDto.getCustomerEmailDto().getUser());
        } else {
            mailDetailHis.setMailFrom(emailFrom);
        }
        mailDetailHis.setMailTo(mailDto.getEmailCustomer());
        mailDetailHis.setCreateDate(new Timestamp(new Date().getTime()));
        mailDetailHis.setUpdateDate(new Timestamp(new Date().getTime()));
        mailDetailHis.setStatus(DbConstant.MAIL_HIS_STATUS_SUCCESS);
        mailDetailHis.setObjectId(mailDto.getObjectId());
        mailDetailHis.setType(mailDto.getType());
        return mailDetailHis;
    }
}
