package compedia.vn.tickmi_mail.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerEmailDto {
    private String user;
    private String password;
    private String host;
    private String port;
    private String connectionTimeOut = "300000";
    private String timeout = "300000";
    private String auth = "true";
    private String sslEnable = "true";
}
