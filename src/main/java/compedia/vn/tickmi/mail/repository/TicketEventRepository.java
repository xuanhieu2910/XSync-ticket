package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.TicketEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketEventRepository extends JpaRepository<TicketEvent, Long>, TicketEventRepositoryCustom {
}
