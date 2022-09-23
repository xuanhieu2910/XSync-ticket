package compedia.vn.tickmi.mail.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailResponse {

    private Integer id;
    private Integer eventId;
    private Integer ticketEventId;
    private Integer objectId;
    private Integer type;
    private Integer providerId;
    private String pathQr;
    private String content;
    private String status;
    private Integer quantity;
    private String nameGuest;
    private String phoneGuest;
    private String emailGuest;
    private String htmlReplace;
    private Integer retry;
    private String emailFrom;
    private String password;
    private String emailHost;
    private String emailPort;
    private String eventName;
}
