package compedia.vn.tickmi.mail.repository.impl;


import compedia.vn.tickmi.mail.dto.EventDto;
import compedia.vn.tickmi.mail.repository.EventRepositoryCustom;
import compedia.vn.tickmi.mail.utils.ValueUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Log4j2
public class EventRepositoryImpl implements EventRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Transactional
    @Override
    public void updateTotalGenTicketEvent(Long eventId, int quantity) {
        Query query = entityManager.createNativeQuery(updateTotalGenTicketEvent);
        query.setParameter("eventId", eventId);
        query.setParameter("quantity", quantity);
        query.executeUpdate();
    }

    @Override
    public EventDto getTotalGenTicket(Long eventId) {
        log.debug("Query get total ticket success event_id = :" + eventId);
        Query query = entityManager.createNativeQuery(getTotalGenTicketCreated);
        query.setParameter("eventId", eventId);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            Object[] obj = result.get(0);
            EventDto dto = new EventDto();
            dto.setTotalGenTicketCreated(ValueUtil.getIntegerByObject(obj[0]));
            dto.setTotalQuantity(ValueUtil.getIntegerByObject(obj[1]));
            return dto;
        }
        return null;
    }

    private static String updateTotalGenTicketEvent = "UPDATE EVENT event  " +
            "SET event.GEN_TICKET_SUCCESS_TOTAL = NVL(event.GEN_TICKET_SUCCESS_TOTAL, 0) + :quantity, event.TOTAL_TICKET_CREATED = NVL(event.TOTAL_TICKET_CREATED, 0) + :quantity " +
            "WHERE event.EVENT_ID = :eventId ";

    private static String getTotalGenTicketCreated = "select NVL(e.TOTAL_TICKET_CREATED, 0), NVL(sum(te.QUANTITY), 0) " +
            "from " +
            "    EVENT e inner join TICKET_EVENT te on e.EVENT_ID = te.EVENT_ID " +
            "where " +
            "        e.EVENT_ID = :eventId " +
            "  and te.STATUS = 1 " +
            "group by e.EVENT_ID, e.TOTAL_TICKET_CREATED";

}
