package compedia.vn.tickmi.mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "MAIL_REQUEST")
public class MailRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "OBJECT_ID")
    private Integer objectId;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "RETRY")
    private Integer retry;

    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    @Column(name = "MODIFIED_TIME")
    private Timestamp modifiedTime;

    @Column(name = "PROVIDER_ID")
    private Integer providerId;

    @Column(name = "EVENT_ID")
    private Integer eventId;

    @Column(name = "TICKET_EVENT_ID")
    private Integer ticketEventId;

    @Column(name = "NAME_GUEST")
    private String nameGuest;

    @Column(name = "PHONE_GUEST")
    private String phoneGuest;

    @Column(name = "EMAIL_GUEST")
    private String emailGuest;
}
