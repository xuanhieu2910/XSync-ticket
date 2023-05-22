package compedia.vn.tickmi.mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "EVENT_REQUEST")
public class EventRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENT_REQUEST")
    private Long Id;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "TICKET_EVENT_ID")
    private Long ticketEventId;

    @Column(name = "OBJECT_ID")
    private Long objectId;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "PROVIDER_ID")
    private Integer providerId;

    @Column(name = "TICKET_GEN")
    private Integer ticketGeneration;

    @Column(name = "NAME_GUEST")
    private String nameGuest;

    @Column(name = "PHONE_GUEST")
    private String phoneGuest;

    @Column(name = "EMAIL_GUEST")
    private String emailGuest;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "IS_DISPLAY_LOGO")
    private Integer isDisplayLogo;

    @Column(name = "IS_DISPLAY_NAME_TICKET")
    private Integer isDisplayName;

    @Column(name = "LOGO_ORGANIZATION")
    private String logoOrganization;

    @Column(name = "AVATAR_PATH")
    private String avatarPath;

    @Column(name = "IS_PACKAGE_FREE")
    private Integer isPackageFree;

    @Column(name = "LIMIT_SCANNER")
    private Integer limitScanner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRequest that = (EventRequest) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public String toString() {
        return "EventRequest{" +
                "Id=" + Id +
                ", status=" + status +
                ", quantity=" + quantity +
                ", eventId=" + eventId +
                ", ticketEventId=" + ticketEventId +
                ", objectId=" + objectId +
                ", type=" + type +
                ", providerId=" + providerId +
                ", ticketGeneration=" + ticketGeneration +
                ", nameGuest='" + nameGuest + '\'' +
                ", phoneGuest='" + phoneGuest + '\'' +
                ", emailGuest='" + emailGuest + '\'' +
                ", note='" + note + '\'' +
                ", isDisplayLogo=" + isDisplayLogo +
                ", isDisplayName=" + isDisplayName +
                ", logoOrganization='" + logoOrganization + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", isPackageFree=" + isPackageFree +
                '}';
    }
}
