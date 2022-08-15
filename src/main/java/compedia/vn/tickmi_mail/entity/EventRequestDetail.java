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
    @Column(name = "ID_DETAILS")
    private Long id;
    @Column(name = "PATH_IMAGE")
    private String pathImage;
    @Column(name = "INDEX_TICKET")
    private Integer indexTicket;
    @Column(name = "CODE_TICKET")
    private String codeTicket;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "RETRY")
    private Integer retry;
    @Column(name = "OBJECT_CONTENT")
    private String objectContent;
    @Column(name = "ID_EVENT_REQUEST")
    private Long eventRequestId;
    @Column(name = "EVENT_ID")
    private Long eventId;
    @Column(name = "TICKET_EVENT_ID")
    private Long ticketEventId;
    @Column(name = "GUEST_ID")
    private Long guestId;
    @Column(name = "TIME_GENERATE")
    private Timestamp timeGenerate;
    @Column(name = "MODIFIED_TIME")
    private Timestamp modifiedTime;
    @Column(name = "PROVIDER_ID")
    private Long providerId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "GUEST_CODE")
    private String guestCode;

}
