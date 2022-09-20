package compedia.vn.tickmi.mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "MailDto{" +
                "id=" + id +
                ", objectId=" + objectId +
                ", type=" + type +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", retry=" + retry +
                ", providerId=" + providerId +
                ", emailCustomer='" + emailCustomer + '\'' +
                ", customerEmailDto=" + customerEmailDto +
                '}';
    }
}
