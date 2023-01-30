package compedia.vn.tickmi.mail.task;

import compedia.vn.tickmi.mail.entity.EventRequest;
import compedia.vn.tickmi.mail.entity.EventRequestDetail;
import compedia.vn.tickmi.mail.service.EventRequestDetailService;
import compedia.vn.tickmi.mail.utils.DbConstant;
import compedia.vn.tickmi.mail.utils.GenerateUtils;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;


@Log4j2
public class CreateEventRequestDetail implements Runnable{


    private EventRequestDetailService eventRequestDetailService;
    private EventRequest eventRequest;

    public CreateEventRequestDetail(EventRequest eventRequest,EventRequestDetailService eventRequestDetailService) {
        this.eventRequest = eventRequest;
        this.eventRequestDetailService = eventRequestDetailService;
    }


    @Override
    public void run() {
        try{
            List<EventRequestDetail> details = new ArrayList<>();
            for (int i = 1; i <= eventRequest.getQuantity(); i++) {
                EventRequestDetail dto = new EventRequestDetail();
                dto.setIndexTicket(i);
                dto.setCodeTicket(GenerateUtils.generateCodeTicket());
                dto.setStatus(DbConstant.STATUS_NEW_EVENT_REQUEST_DETAIL);
                dto.setRetry(DbConstant.INIT_RETRY);
                dto.setEventId(eventRequest.getEventId());
                dto.setTicketEventId(eventRequest.getTicketEventId());
                dto.setProviderId(eventRequest.getProviderId());
                dto.setObjectId(eventRequest.getObjectId());
                dto.setType(eventRequest.getType());
                dto.setEventRequestId(eventRequest.getId());
                dto.setNameGuest(eventRequest.getNameGuest());
                dto.setPhoneGuest(eventRequest.getPhoneGuest());
                dto.setEmailGuest(eventRequest.getEmailGuest());
                dto.setIsDisplayLogo(eventRequest.getIsDisplayLogo());
                dto.setIsDisplayName(eventRequest.getIsDisplayName());
                dto.setPathLogo(eventRequest.getLogoOrganization());
                dto.setNote(eventRequest.getNote());
                if (null != eventRequest.getAvatarPath()) {
                    dto.setPathLogo(eventRequest.getAvatarPath());
                }
                details.add(dto);
            }
            log.info("Size details : {} ", details.size());
            eventRequestDetailService.saveEventRequestDetails(details);
            log.info(" SAVE: Event request detail success: " + details.toString());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
