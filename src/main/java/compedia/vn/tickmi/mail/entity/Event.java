package compedia.vn.tickmi.mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "EVENT")
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID", nullable = false)
    private Long eventId;

    @Column(name = "PROVIDER_ID")
    private Long providerId;

    @Column(name = "FIELD_ID")
    private Long fieldId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "START_DATE")
    private Timestamp startDate;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Column(name = "IMAGE_BANNER")
    private String imageBanner;

    @Column(name = "IMAGE_POSITION")
    private String imagePosition;

    @Column(name = "EVENT_TYPE")
    private Integer eventType;

    @Column(name = "IS_HIDE")
    private Integer isHide;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "UPDATE_BY")
    private Long updateBy;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "DISTRICT_ID")
    private Long districtId;

    @Column(name = "COMMUNE_ID")
    private Long communeId;

    @Column(name = "VIEW_EVENT")
    private Long viewEvent;

    @Column(name = "ADDRESS_CUSTOM")
    private String addressCustom;

    @Column(name = "NATION_ID")
    private Long nationId;

    @Column(name = "TAG_NAME")
    private String tagName;

    @Column(name = "IS_DRAW")
    private Integer isDraw;

    @Column(name = "COUNT_COPY")
    private Integer countCopy;

    @Column(name = "ORGANIZATION_NAME")
    private String nameOrganization;

    @Column(name = "DESCRIPTION_ORGANIZATION")
    private String descriptionOrganization;

    @Column(name = "LOGO_ORGANIZATION")
    private String logoOrganization;

    @Column(name = "SCAN_TICKET_SUCCESS_TOTAL")
    private Integer scanTicketSuccessTotal;

    @Column(name = "GEN_TICKET_SUCCESS_TOTAL")
    private Integer genTicketSuccessTotal;
}
