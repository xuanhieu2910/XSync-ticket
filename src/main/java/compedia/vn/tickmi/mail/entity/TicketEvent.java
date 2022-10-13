package compedia.vn.tickmi.mail.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TICKET_EVENT")
@Getter
@Setter
@NoArgsConstructor
public class TicketEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_EVENT_ID")
    private Long ticketEventId;

    @Column(name = "CODE_TICKET_EVENT")
    private String codeTicketEvent;

    @Column(name = "NAME_TICKET")
    private String nameTicket;

    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "TYPE_TICKET")
    private Integer typeTicket;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "TEMPLATE_TICKET_ID")
    private Long templateTicketId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "IS_SELL")
    private Integer isSell;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "CREATE_BY")
    private Integer createBy;

    @Column(name = "DESCRIPTION")
    private String description;
}