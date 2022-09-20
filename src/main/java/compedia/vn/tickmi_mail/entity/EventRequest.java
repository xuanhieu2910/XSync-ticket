package compedia.vn.tickmi_mail.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "EVENT_ID")
    private Integer eventId;

    @Column(name = "TICKET_EVENT_ID")
    private Integer ticketEventId;

    @Column(name = "OBJECT_ID")
    private Integer objectId;

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

}
