package compedia.vn.tickmi_mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "EVENT_REQUEST")
public class EventRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENT_REQUEST")
    private Long Id;
    @Column(name = "GUEST_ID")
    private Long guestId;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;
    @Column(name = "MODIFIED_TIME")
    private Timestamp modifiedTime;
    @Column(name = "PROVIDER_ID")
    private Long providerId;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Column(name = "EVENT_ID")
    private Long eventId;
    @Column(name = "TICKET_EVENT_ID")
    private Long ticketEventId;
    @Column(name = "QUANTITY_GEN")
    private Integer quantityGen;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Integer getQuantityGen() {
        return quantityGen;
    }

    public void setQuantityGen(Integer quantityGen) {
        this.quantityGen = quantityGen;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getTicketEventId() {
        return ticketEventId;
    }

    public void setTicketEventId(Long ticketEventId) {
        this.ticketEventId = ticketEventId;
    }
}
