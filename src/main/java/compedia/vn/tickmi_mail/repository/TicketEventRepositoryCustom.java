package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.TicketEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface TicketEventRepositoryCustom {

    Optional<TicketEvent> findTicketsEventById(Integer id) throws IOException, SQLException;
}
