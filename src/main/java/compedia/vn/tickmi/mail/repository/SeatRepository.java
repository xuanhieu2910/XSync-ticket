package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryCustom {
}
