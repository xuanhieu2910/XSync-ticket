package compedia.vn.tickmi.mail.repository.impl;

import compedia.vn.tickmi.mail.repository.SeatRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SeatRepositoryImpl implements SeatRepositoryCustom {

    private final static Logger logger = LoggerFactory.getLogger(SeatRepositoryImpl.class);

    @Autowired
    EntityManager entityManager;

    @Transactional
    @Override
    public boolean updateUsedStatusBySeatCode(String seatCode, Long eventId) {

        StringBuilder sb = new StringBuilder();
        sb.append("update SEAT set STATUS = 3 where EVENT_ID = :eventId and CODE = :seatCode");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("eventId", eventId);
        query.setParameter("seatCode", seatCode);
        try {
            return query.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
