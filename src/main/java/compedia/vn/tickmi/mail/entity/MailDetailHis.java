package compedia.vn.tickmi.mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "MAIL_DETAIL_HIS")
public class MailDetailHis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MAIL_FROM")
    private String mailFrom;

    @Column(name = "MAIL_TO")
    private String mailTo;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "UPDATE_DATE")
    private Timestamp updateDate;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "OBJECT_ID")
    private Long objectId;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "TICKET_EVENT_ID")
    private Long ticketEventId;

    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "PROVIDER_ID")
    private Integer providerId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "NAME_GUEST")
    private String nameGuest;

    @Column(name = "PHONE_GUEST")
    private String phoneGuest;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IS_PACKAGE_FREE")
    private Integer isPackageFree;
}
