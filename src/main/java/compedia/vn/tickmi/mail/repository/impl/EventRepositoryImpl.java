package compedia.vn.tickmi.mail.repository.impl;


import compedia.vn.tickmi.mail.repository.EventRepositoryCustom;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class EventRepositoryImpl implements EventRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Transactional
    @Override
    public void updateTotalGenTicketEvent(Long eventId) {
        Query query = entityManager.createNativeQuery(updateTotalGenTicketEvent);
        query.setParameter("eventId",eventId);
    }


    private static String updateTotalGenTicketEvent = "UPDATE EVENT event  " +
            "SET event.GEN_TICKET_SUCCESS_TOTAL = event.GEN_TICKET_SUCCESS_TOTAL + 1 " +
            "WHERE event.EVENT_ID = :eventId ";

}
