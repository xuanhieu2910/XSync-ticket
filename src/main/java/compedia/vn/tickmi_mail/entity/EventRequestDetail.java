package compedia.vn.tickmi_mail.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public Integer getIndexTicket() {
        return indexTicket;
    }

    public void setIndexTicket(Integer indexTicket) {
        this.indexTicket = indexTicket;
    }

    public String getCodeTicket() {
        return codeTicket;
    }

    public void setCodeTicket(String codeTicket) {
        this.codeTicket = codeTicket;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public String getObjectContent() {
        return objectContent;
    }

    public void setObjectContent(String objectContent) {
        this.objectContent = objectContent;
    }

    public Long getEventRequestId() {
        return eventRequestId;
    }

    public void setEventRequestId(Long eventRequestId) {
        this.eventRequestId = eventRequestId;
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

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Timestamp getTimeGenerate() {
        return timeGenerate;
    }

    public void setTimeGenerate(Timestamp timeGenerate) {
        this.timeGenerate = timeGenerate;
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
}
