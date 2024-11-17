package compedia.vn.tickmi.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SEAT")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEAT_ID", nullable = false)
    private Long seatId;

    @Column(name = "FLOOR")
    private Long floor;

    @Column(name = "ROW")
    private String row;

    @Column(name = "SEAT_NUM")
    private Long seatNum;

    @Column(name = "CODE")
    private String code;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "SORT_BY")
    private Long sortBy;

    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "BOOK_DATE")
    private Date bookDate;
}
