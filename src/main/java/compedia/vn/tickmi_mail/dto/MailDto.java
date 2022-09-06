package compedia.vn.tickmi_mail.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDto {

    private String emailTo;
    private String subject;
    private String content;
    private String rollBackQuery;
    private CustomerEmailDto customerEmailDto;


    public MailDto(String emailTo, String subject, String content, CustomerEmailDto customerEmailDto) {
        this.emailTo = emailTo;
        this.subject = subject;
        this.content = content;
        this.rollBackQuery = rollBackQuery;
        this.customerEmailDto = customerEmailDto;
    }


}
