package compedia.vn.tickmi.mail.service;

import compedia.vn.tickmi.mail.entity.Ticket;
import compedia.vn.tickmi.mail.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {


    private final static Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    TicketRepository ticketRepository;

    public Ticket saveTicket(Ticket ticket) {
        logger.info("Service start save all ticket!");
        return ticketRepository.save(ticket);
    }

}
