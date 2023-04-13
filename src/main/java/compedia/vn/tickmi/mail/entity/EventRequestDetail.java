package compedia.vn.tickmi.mail.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "EVENT_REQUEST_DETAILS")
public class EventRequestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST_DETAILS", updatable = false, nullable = false)
    private Long id;

    @Column(name = "INDEX_TICKET")
    private Integer indexTicket;

    @Column(name = "CODE_TICKET")
    private String codeTicket;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "RETRY")
    private Integer retry;

    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "TICKET_EVENT_ID")
    private Long ticketEventId;

    @Column(name = "PROVIDER_ID")
    private Integer providerId;

    @Column(name = "OBJECT_ID")
    private Long objectId;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "EVENT_REQUEST_ID")
    private Long eventRequestId;

    @Column(name = "NAME_GUEST")
    private String nameGuest;

    @Column(name = "PHONE_GUEST")
    private String phoneGuest;

    @Column(name = "EMAIL_GUEST")
    private String emailGuest;

    @Column(name = "IS_DISPLAY_LOGO")
    private Integer isDisplayLogo;

    @Column(name = "IS_DISPLAY_NAME")
    private Integer isDisplayName;

    @Column(name = "PATH_LOGO")
    private String pathLogo;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "AVATAR_PATH")
    private String avatarPath;

    @Column(name = "IS_PACKAGE_FREE")
    private Integer isPackageFree;

    @Override
    public String toString() {
        return "EventRequestDetail{" +
                "id=" + id +
                ", indexTicket=" + indexTicket +
                ", codeTicket='" + codeTicket + '\'' +
                ", status=" + status +
                ", retry=" + retry +
                ", eventId=" + eventId +
                ", ticketEventId=" + ticketEventId +
                ", providerId=" + providerId +
                ", objectId=" + objectId +
                ", type=" + type +
                ", eventRequestId=" + eventRequestId +
                ", nameGuest='" + nameGuest + '\'' +
                ", phoneGuest='" + phoneGuest + '\'' +
                ", emailGuest='" + emailGuest + '\'' +
                ", isDisplayLogo=" + isDisplayLogo +
                ", isDisplayName=" + isDisplayName +
                ", pathLogo='" + pathLogo + '\'' +
                ", note='" + note + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", isPackageFree=" + isPackageFree +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRequestDetail that = (EventRequestDetail) o;
        return Objects.equals(id, that.id);
    }
}
