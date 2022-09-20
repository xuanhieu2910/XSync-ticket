package compedia.vn.tickmi_mail.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDto {

    private Integer id;
    private Integer objectId;
    private Integer type;
    private String subject;
    private String content;
    private Integer retry;
    private Integer providerId;
    private String emailCustomer;
    private CustomerEmailDto customerEmailDto;


    public MailDto(String emailCustomer, String subject, String content, CustomerEmailDto customerEmailDto) {
        this.emailCustomer = emailCustomer;
        this.subject = subject;
        this.content = content;
        this.customerEmailDto = customerEmailDto;
    }


}
