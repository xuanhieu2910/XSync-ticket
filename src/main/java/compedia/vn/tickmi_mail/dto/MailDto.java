package compedia.vn.tickmi_mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {

    private String emailTo;
    private String subject;
    private String content;
    private String rollBackQuery;
    private CustomerEmailDto customerEmailDto;
}
