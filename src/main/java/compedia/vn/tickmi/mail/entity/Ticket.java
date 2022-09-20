package compedia.vn.tickmi.mail.entity;

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

    @Column(name = "PROVIDER_ID")
    private Integer providerId;

    @Column(name = "TICKET_EVENT_ID")
    private Integer ticketEventId;

    @Column(name = "EVENT_ID")
    private Integer eventId;

    @Column(name = "PATH_QR")
    private String pathQr;

    @Column(name = "TIME_GENERATE")
    private Timestamp timeGenerate;

    @Column(name = "MODIFIED_TIME")
    private Timestamp modifiedTime;

    @Column(name = "INDEX_QR")
    private Integer indexQr;

    @Column(name = "TICKET_CODE")
    private String ticketCode;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "OBJECT_ID")
    private Integer objectId;

    @Column(name = "TYPE")
    private Integer type;
}
