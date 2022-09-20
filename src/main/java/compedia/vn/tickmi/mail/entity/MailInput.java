package compedia.vn.tickmi.mail.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "MAIL_INPUT")
@Getter
@Setter
@NoArgsConstructor
public class MailInput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MAIL_INPUT")
    private Integer id;

    @Column(name = "OBJECT_ID")
    private Integer objectId;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "RETRY")
    private Integer retry;

    @Column(name = "PROVIDER_ID")
    private Integer providerId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "EMAIL_CUSTOMER")
    private String emailCustomer;

    @Column(name = "SUBJECT")
    private String subject;
}
