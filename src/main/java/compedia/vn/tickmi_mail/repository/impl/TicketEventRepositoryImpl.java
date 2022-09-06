package compedia.vn.tickmi_mail.repository.impl;

import compedia.vn.tickmi_mail.entity.TicketEvent;
import compedia.vn.tickmi_mail.repository.TicketEventRepositoryCustom;
import compedia.vn.tickmi_mail.utils.ValueUtil;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TicketEventRepositoryImpl implements TicketEventRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<TicketEvent> findTicketsEventById(Integer id) throws IOException, SQLException {
        Query query = entityManager.createNativeQuery(SQL_findTicketsEventById);
        query.setParameter("id",id);
        List<Object[]> resultList = query.getResultList();
        if (!CollectionUtils.isEmpty(resultList)) {
                Object [] obj = resultList.get(0);
                TicketEvent event = new TicketEvent();
                event.setTicketEventId(ValueUtil.getIntegerByObject(obj[0]));
                event.setCodeTicketEvent(ValueUtil.getStringByObject(obj[1]));
                event.setNameTicket(ValueUtil.getStringByObject(obj[2]));
                event.setEventId(ValueUtil.getIntegerByObject(obj[3]));
                event.setTypeTicket(ValueUtil.getIntegerByObject(obj[4]));
                event.setPrice(ValueUtil.getDoubleByObject(obj[5]));
                event.setTemplateTicketId(ValueUtil.getIntegerByObject(obj[6]));
                event.setQuantity(ValueUtil.getIntegerByObject(obj[7]));
                event.setLimitAmount(ValueUtil.getIntegerByObject(obj[8]));
                event.setStatus(ValueUtil.getIntegerByObject(obj[9]));
                Date registerStartDate = ValueUtil.getDateByObject(obj[10]);
                Date registerEndDate = ValueUtil.getDateByObject(obj[11]);
                if (registerStartDate != null ) {
                    event.setRegisterStartDate(registerStartDate);
                }
                if (registerEndDate != null ) {
                    event.setRegisterEndDate(registerEndDate);
                }
                event.setIsSell(ValueUtil.getIntegerByObject(obj[12]));
                event.setCreateDate(ValueUtil.getDateByObject(obj[13]));
                event.setModifiedDate(ValueUtil.getDateByObject(obj[14]));
                event.setCreateBy(ValueUtil.getIntegerByObject(obj[15]));
                event.setDescription(ValueUtil.getStringByObject(obj[16]));
                event.setDesignHtml(ValueUtil.getClobString((Clob) obj[17]) == null ? null : ValueUtil.getClobString((Clob) obj[17]) );
                event.setHtml(ValueUtil.getClobString((Clob) obj[18]) == null ? null : ValueUtil.getClobString((Clob) obj[18]) );
                return Optional.of(event);
            }
        return Optional.empty();
    }

    private static final String SQL_findTicketsEventById = "select ticketEvent.* +" +
            "                 from TICKET_EVENT ticketEvent +" +
            "                 where ticketEvent.TICKET_EVENT_ID = :id +" +
            "                        and ticketEvent.STATUS = 1";
}
