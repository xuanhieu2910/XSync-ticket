package compedia.vn.tickmi.mail.utils;

import compedia.vn.tickmi.mail.dto.MailDto;
import compedia.vn.tickmi.mail.dto.CustomerEmailDto;
import compedia.vn.tickmi.mail.dto.SmtpAuthenticator;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class MailUtils implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(MailUtils.class);

    private static MailUtils INSTANCE = null;

    private final SmtpAuthenticator smtpAuthenticator;
    private final Queue<MailDto> mailDtoQueue;

    public static MailUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MailUtils();
            new Thread(INSTANCE).start();
        }
        return INSTANCE;
    }

    public MailUtils() {
        String email = PropertiesUtil.getEmailProperty("mail.user");
        String password = PropertiesUtil.getEmailProperty("mail.password");
        smtpAuthenticator = new SmtpAuthenticator(email, password);
        mailDtoQueue = new LinkedList<>();
    }

    @Synchronized
    public void sendTicketEmail(String emailTo, String content, String subject, CustomerEmailDto customerEmailDto) {
        MailDto mailDto = new MailDto(emailTo, subject, content, customerEmailDto);
        mailDtoQueue.add(mailDto);
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
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                MailDto mailDto = mailDtoQueue.poll();
                if (mailDto != null) {
                    String rs = send(mailDto) ? "success" : "fail";
                    log.info("Send mail is " + rs + " (" + mailDto + ")");
                }
            } catch (Exception e) {
                log.error("Lỗi", e);
            }
        }
    }
}
