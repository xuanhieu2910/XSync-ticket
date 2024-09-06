package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.dto.EventDto;
public interface EventRepositoryCustom {

    void updateTotalGenTicketEvent (Long eventId, int value);

    EventDto getTotalGenTicket(Long eventId);
}
