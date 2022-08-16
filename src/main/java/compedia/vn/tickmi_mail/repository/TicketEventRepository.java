package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.TicketEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketEventRepository extends JpaRepository<TicketEvent,Long>, TicketEventRepositoryCustom {
}
