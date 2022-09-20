package compedia.vn.tickmi.mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InformationMailDto {
    private String emailUser;
    private String emailPassword;
    private String emailHost;
    private String emailPort;
    private String html;
    private List<String> pathQr;
    private String emailTo;
    private int guestId;
    private String guestName;
    private String content;
}
