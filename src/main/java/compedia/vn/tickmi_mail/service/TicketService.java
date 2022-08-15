package compedia.vn.tickmi_mail.service;

import compedia.vn.tickmi_mail.entity.Ticket;
import compedia.vn.tickmi_mail.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {


    private final static Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    TicketRepository ticketRepository;

    public List<Ticket> saveAllTickets (List<Ticket> ticketList) {
        logger.info("Service start save all ticket!");
        return ticketRepository.saveAll(ticketList);
    }

}
