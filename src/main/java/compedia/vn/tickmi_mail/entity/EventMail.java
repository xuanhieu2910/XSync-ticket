package compedia.vn.tickmi_mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name="EVENT_MAIL")
public class EventMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENT_MAIL")
    private Long id;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "RETRY")
    private Integer retry;
    @Column(name = "TICKET_ID")
    private Long ticketId;
    @Column(name =  "PROVIDER_ID")
    private Long providerId;

}
