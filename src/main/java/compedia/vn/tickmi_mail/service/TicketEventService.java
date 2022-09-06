package compedia.vn.tickmi_mail.service;

import compedia.vn.tickmi_mail.entity.TicketEvent;
import compedia.vn.tickmi_mail.repository.TicketEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class TicketEventService {

    private final static Logger logger = LoggerFactory.getLogger(TicketEventService.class);

    @Autowired
    TicketEventRepository ticketEventRepository;

    public Optional<TicketEvent> findTicketEventById (Integer ticketEventId) throws IOException, SQLException {
        return ticketEventRepository.findTicketsEventById(ticketEventId);
    }
}
