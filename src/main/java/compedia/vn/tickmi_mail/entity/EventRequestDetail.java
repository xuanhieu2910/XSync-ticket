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
@Table(name = "EVENT_REQUEST_DETAILS")
public class EventRequestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST_DETAILS")
    private Integer id;


    @Column(name = "INDEX_TICKET")
    private Integer indexTicket;

    @Column(name = "CODE_TICKET")
    private String codeTicket;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "RETRY")
    private Integer retry;

    @Column(name = "EVENT_ID")
    private Integer eventId;

    @Column(name = "TICKET_EVENT_ID")
    private Integer ticketEventId;

    @Column(name = "PROVIDER_ID")
    private Integer providerId;

    @Column(name = "OBJECT_ID")
    private Integer objectId;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "GUEST_NAME")
    private String guestName;


}
