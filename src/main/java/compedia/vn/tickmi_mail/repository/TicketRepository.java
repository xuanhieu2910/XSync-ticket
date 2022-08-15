package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository  extends JpaRepository<Ticket,Long> , TicketRepositoryCustom {
}
