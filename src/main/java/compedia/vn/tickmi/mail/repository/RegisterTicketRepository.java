package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.RegisterTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RegisterTicketRepository extends JpaRepository<RegisterTicket, Long> {

    @Query("SELECT u.stt FROM RegisterTicket u WHERE u.idRegisterTicket = (select rtd.idRegisterTicket from RegisterTicketDetails rtd where rtd.idRegisterTicketDetails = ?1)")
    String getStt(Long idRegisterTicketDetails);
}
