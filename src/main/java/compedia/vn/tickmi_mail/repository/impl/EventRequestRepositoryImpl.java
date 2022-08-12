package compedia.vn.tickmi_mail.repository.impl;

import compedia.vn.tickmi_mail.entity.EventRequest;
import compedia.vn.tickmi_mail.repository.EventRequestRepositoryCustom;
import compedia.vn.tickmi_mail.utils.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EventRequestRepositoryImpl implements EventRequestRepositoryCustom {


    private static final Logger logger = LoggerFactory.getLogger(EventRequestRepositoryImpl.class);

    @Autowired
    EntityManager entityManager;

    @Override
    public void deleteEventsRequest(List<Long> eventRequestIds) {

    }

    @Override
    public List<EventRequest> getEventRequestByStatus(Integer status, Integer limit) {
        StringBuilder sb = new StringBuilder();
        sb.append("            select evenRequest.*" +
                "            from EVENT_REQUEST evenRequest" +
                "            where evenRequest.STATUS = :status" +
                "              and ROWNUM < :limitRow");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status",status);
        query.setParameter("limitRow",limit);
        List<Object[]> result = query.getResultList();
        List<EventRequest>response = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                EventRequest dto = new EventRequest();
                dto.setId(ValueUtil.getLongByObject(obj[0]));
                dto.setGuestId(ValueUtil.getLongByObject(obj[1]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                dto.setCreateTime(ValueUtil.getTimestampByObject(obj[3]));
                dto.setModifiedTime(ValueUtil.getTimestampByObject(obj[4]));;
                dto.setProviderId(ValueUtil.getLongByObject(obj[5]));
                dto.setFlatSend(ValueUtil.getIntegerByObject(obj[6]));
                dto.setQuantity(ValueUtil.getLongByObject(obj[7]));
                dto.setEventId(ValueUtil.getLongByObject(obj[8]));
                dto.setTicketEventId(ValueUtil.getLongByObject(obj[9]));
                response.add(dto);
            }
        }
        return response;
    }
}
