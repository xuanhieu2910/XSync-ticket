package compedia.vn.tickmi.mail.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "EVENT_REQUEST_HIS")
@Getter
@Setter
public class EventRequestHis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENT_REQUEST_HIS")
    private Long idEventRequestHis;

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
    private Integer ticketGen;

    @Column(name = "NAME_GUEST")
    private String nameGuest;

    @Column(name = "PHONE_GUEST")
    private String phoneGuest;

    @Column(name = "EMAIL_GUEST")
    private String emailGuest;

    @Column(name = "ID_EVENT_REQUEST")
    private Long idEventRequest;

    @Override
    public String toString() {
        return "EventRequestHis{" +
                "idEventRequestHis=" + idEventRequestHis +
                ", status=" + status +
                ", quantity=" + quantity +
                ", eventId=" + eventId +
                ", ticketEventId=" + ticketEventId +
                ", objectId=" + objectId +
                ", type=" + type +
                ", providerId=" + providerId +
                ", ticketGen=" + ticketGen +
                ", nameGuest='" + nameGuest + '\'' +
                ", phoneGuest='" + phoneGuest + '\'' +
                ", emailGuest='" + emailGuest + '\'' +
                ", idEventRequest=" + idEventRequest +
                '}';
    }
}
