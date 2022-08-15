package compedia.vn.tickmi_mail.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
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
    @Column(name =  "PROVIDER_ID")
    private Long providerId;
    @Column(name = "TICK_EVENT_ID")
    private Long tickEventId;
    @Column(name = "EVENT_ID")
    private Long eventId;
    @Column(name = "GUEST_ID")
    private Long guestId;
    @Column(name = "CODE_TICKET")
    private String codeTicket;

}
