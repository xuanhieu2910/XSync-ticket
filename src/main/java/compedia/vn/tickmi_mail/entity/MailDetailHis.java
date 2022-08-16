package compedia.vn.tickmi_mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "MAIL_DETAIL_HIS")
public class MailDetailHis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MAIL_FROM")
    private String mailFrom;
    @Column(name = "MAIL_TO")
    private String mailTo;
    @Column(name = "GUEST_ID")
    private Long guestId;
    @Column(name = "CREATE_DATE")
    private Timestamp createDate;
    @Column(name = "UPDATE_DATE")
    private Timestamp updateDate;
    @Column(name = "STATUS")
    private Integer status;
}
