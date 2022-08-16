package compedia.vn.tickmi_mail.dto;

public class MailDto {

    private String emailTo;
    private String subject;
    private String content;

    private String rollBackQuery;

    private CustomerEmailDto customerEmailDto;


    public MailDto() {
    }

    public MailDto(String emailTo, String subject, String content) {
        this.emailTo = emailTo;
        this.subject = subject;
        this.content = content;
    }


    public MailDto(String emailTo, String subject, String content, CustomerEmailDto customerEmailDto) {
        this.emailTo = emailTo;
        this.subject = subject;
        this.content = content;
        this.customerEmailDto = customerEmailDto;
    }

    public MailDto(String emailTo, String subject, String content, CustomerEmailDto customerEmailDto, String rollBackQuery) {
        this.emailTo = emailTo;
        this.subject = subject;
        this.content = content;
        this.rollBackQuery = rollBackQuery;
        this.customerEmailDto = customerEmailDto;
    }


    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getRollBackQuery() {
        return rollBackQuery;
    }

    public void setRollBackQuery(String rollBackQuery) {
        this.rollBackQuery = rollBackQuery;
    }

    public CustomerEmailDto getCustomerEmailDto() {
        return customerEmailDto;
    }

    public void setCustomerEmailDto(CustomerEmailDto customerEmailDto) {
        this.customerEmailDto = customerEmailDto;
    }

    @Override
    public String toString() {
        return "To: " + emailTo + ", Subject: " + subject + ", Content: " + content;
    }
}
