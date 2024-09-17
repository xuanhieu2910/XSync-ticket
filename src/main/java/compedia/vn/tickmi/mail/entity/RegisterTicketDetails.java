package compedia.vn.tickmi.mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "REGISTER_TICKET_DETAILS")
@Getter
@Setter
public class RegisterTicketDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REGISTER_TICKET_DETAILS")
    private Long idRegisterTicketDetails;
    @Column(name = "TICKET_EVENT_ID")
    private Long ticketEventId;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "TIME_ACCEPT")
    private Timestamp timeAccept;
    @Column(name = "STATUS_ACCEPT")
    private Integer statusAccept;
    @Column(name = "ID_REGISTER_TICKET")
    private Long idRegisterTicket;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "STATUS_GEN_TICKET")
    private Integer statusGenTicket;
    @Column(name = "STATUS_SEND_MAIL")
    private Integer statusSendMail;
    @Column(name = "STATUS_SEND_ZALO")
    private Integer statusSendZalo;
}
