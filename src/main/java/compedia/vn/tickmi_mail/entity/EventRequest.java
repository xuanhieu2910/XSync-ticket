package compedia.vn.tickmi_mail.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "EVENT_REQUEST")
public class EventRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENT_REQUEST")
    private Integer Id;
    @Column(name = "GUEST_ID")
    private Integer guestId;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;
    @Column(name = "MODIFIED_TIME")
    private Timestamp modifiedTime;
    @Column(name = "PROVIDER_ID")
    private Integer providerId;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "EVENT_ID")
    private Integer eventId;
    @Column(name = "TICKET_EVENT_ID")
    private Integer ticketEventId;
    @Column(name = "QUANTITY_GEN")
    private Integer quantityGen;
    @Column(name = "GUEST_CODE")
    private String guestCode;
    @Column(name = "USER_ID")
    private Integer userId;

    @Override
    public String toString() {
        return "EventRequest{" +
                "Id=" + Id +
                ", guestId=" + guestId + "\n" +
                ", status=" + status + "\n" +
                ", createTime=" + createTime + "\n" +
                ", modifiedTime=" + modifiedTime + "\n" +
                ", providerId=" + providerId + "\n" +
                ", quantity=" + quantity + "\n" +
                ", eventId=" + eventId + "\n" +
                ", ticketEventId=" + ticketEventId + "\n" +
                ", quantityGen=" + quantityGen + "\n" +
                '}';
    }
}
