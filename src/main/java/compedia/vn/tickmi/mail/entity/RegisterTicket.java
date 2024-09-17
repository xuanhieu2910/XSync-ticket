package compedia.vn.tickmi.mail.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "REGISTER_TICKET")
@Getter
@Setter
@NoArgsConstructor
public class RegisterTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REGISTER_TICKET")
    private Long idRegisterTicket;

    @Column(name = "NAME_USER_REGISTER")
    private String nameUserRegister;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "USER_ACCEPT")
    private Long userId;

    @Column(name = "TIME_ACCEPT")
    private Timestamp timeAccept;

    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "STT")
    private Long stt;

    @Column(name = "ZALO_SENT_STATUS")
    private Integer zaloSentStatus;

    @Column(name = "ZALO_SENT_DATE")
    private Timestamp zaloSentDate;
}
