package compedia.vn.tickmi_mail.task;

import compedia.vn.tickmi_mail.dto.CustomerEmailDto;
import compedia.vn.tickmi_mail.dto.MailDto;
import compedia.vn.tickmi_mail.dto.SmtpAuthenticator;
import compedia.vn.tickmi_mail.utils.PropertiesUtil;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Queue;

@Log4j2
public class SendMail implements Runnable{

    private static SendMail INSTANCE = null;
    private static Queue<MailDto> mailDtoQueue;


    public static SendMail getInstance() {
        if (INSTANCE != null) {
            INSTANCE = new SendMail();
            Thread thread = new Thread(INSTANCE);
            thread.start();
        }
        return INSTANCE;
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDto.getEmailTo()));
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
        while(!mailDtoQueue.isEmpty()) {
            try {
                MailDto mailDto = mailDtoQueue.poll();
                String rs = send(mailDto) ? "success" : "fail";
                log.info("Send mail is " + rs + " (" + mailDto + ")");
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
    }
}
