package compedia.vn.tickmi_mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_ID")
    private Integer ticketId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "TICKET_EVENT_ID")
    private Integer ticketEventId;

    @Column(name = "EVENT_ID")
    private Integer eventId;

    @Column(name = "GUEST_ID")
    private Integer guestId;

    @Column(name = "PATH_QR")
    private String pathQr;

    @Column(name = "TIME_GENERATE")
    private Timestamp TIME_GENERATE;

    @Column(name = "INDEX_QR")
    private Integer indexQr;

    @Column(name = "GUEST_CODE")
    private String guestCode;

    @Column(name = "TICKET_CODE")
    private String ticketCode;

    @Column(name = "STATUS")
    private Integer status;
}
