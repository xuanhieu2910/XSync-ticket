package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.EventRequestDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventRequestDetailRepositoryImpl implements EventRequestRepositoryDetailCustom {

    private final static Logger logger = LoggerFactory.getLogger(EventRequestDetailRepositoryImpl.class);

    @Override
    public List<EventRequestDetail> findEventRequestDetailsLimit(Integer limits, Integer status) {
        return null;
    }

    @Override
    public void updateEventRequestDetails(List<EventRequestDetail> details) {

    }
}
