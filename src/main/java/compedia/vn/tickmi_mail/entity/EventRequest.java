package compedia.vn.tickmi_mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "EVENT_REQUEST")
public class EventRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENT_REQUEST")
    private Long Id;
    @Column(name = "GUEST_ID")
    private Long guestId;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;
    @Column(name = "MODIFIED_TIME")
    private Timestamp modifiedTime;
    @Column(name = "PROVIDER_ID")
    private Long providerId;
    @Column(name = "FLAT_SEND")
    private Integer flatSend;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Column(name = "EVENT_ID")
    private Long eventId;
    @Column(name = "TICKET_EVENT_ID")
    private Long ticketEventId;
}
