package compedia.vn.tickmi_mail.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "MAIL_ROOT")
public class MailRoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "GUEST_ID")
    private Long guestId;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "RETRY")
    private Integer retry;
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;
    @Column(name = "MODIFIED_TIME")
    private Timestamp modifiedTime;
    @Column(name = "PROVIDER_ID")
    private Long providerId;
}
