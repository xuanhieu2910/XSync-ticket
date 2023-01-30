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
    private Long id;

    @Column(name = "OBJECT_ID")
    private Long objectId;

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
    private Long eventId;

    @Column(name = "TICKET_EVENT_ID")
    private Long ticketEventId;

    @Column(name = "NAME_GUEST")
    private String nameGuest;

    @Column(name = "PHONE_GUEST")
    private String phoneGuest;

    @Column(name = "EMAIL_GUEST")
    private String emailGuest;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "AVATAR_PATH")
    private String avatarPath;

    @Override
    public String toString() {
        return "MailRequest{" +
                "id=" + id +
                ", objectId=" + objectId +
                ", type=" + type +
                ", status=" + status +
                ", retry=" + retry +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", providerId=" + providerId +
                ", eventId=" + eventId +
                ", ticketEventId=" + ticketEventId +
                ", nameGuest='" + nameGuest + '\'' +
                ", phoneGuest='" + phoneGuest + '\'' +
                ", emailGuest='" + emailGuest + '\'' +
                ", quantity=" + quantity +
                ", note='" + note + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                '}';
    }
}
