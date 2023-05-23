package compedia.vn.tickmi.mail.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class DbConstant {

    @Autowired
    private Environment env;

    @PostConstruct
    public void setUpConfigData() {
        URL = env.getProperty("vn.compedia.location.upload");
    }

    public static String URL;

    // Flat to run job
    public  static boolean IS_FLAT_RUN_JOB = false;
    public static Integer IS_FLAT_DISPLAY_LOGO = 1;
    public static Integer IS_FLAT_DISPLAY_NAME_TICKET = 1;

    // Retry
    public final static Integer INIT_RETRY = 0;

    // Limit size to query db get value
    public static final Integer SIZE_LIMIT = 20;

    // Extension qr
    public static final String[] EXTENSION_GENERATE_QR = {"png", "img", "jpg"};

    // Style qr
    public static final Integer WIDTH_QR = 400;
    public static final Integer HEIGHT_QR = 400;
    public static final Integer PADDING_QR = 3;
    public static final Integer POSITION_NAME = 33;

    // Style Logo
    public static final Integer WIDTH_LOGO = 60;
    public static final Integer HEIGHT_LOGO = 60;

    // Event request
    public final static Integer MAX_RETRY = 3;
    public final static Integer STATUS_EVENT_REQUEST = 1;

    // Event request detail
    public final static Integer MAX_RETRY_DETAIL = 3;
    public final static Integer STATUS_EVENT_REQUEST_DETAIL = 1;
    public final static Integer STATUS_NEW_EVENT_REQUEST_DETAIL = -1;
    // Event mail
    public final static Integer EVENT_MAIL_NEW = -1;

    // MailRoot
    public final static Integer MAIL_ROOT_STATUS_NEW = -1;

    // Ticket
    public final static Integer TICKET_NOT_CHECKIN = 0;
    public final static Integer TICKET_FALSE = 2;
    public final static Integer DEFAULT_NUMBER_TICKET_SCANNED = 0;

    // Ticket mail his
    public final static Integer MAIL_HIS_STATUS_SUCCESS = 1;


    // Status gen and sen ticket
    public static final Integer STATUS_PROVED_NEW = -1;
    public static final Integer STATUS_PROVED_PROCESS = 0;
    public static final Integer STATUS_PROVED_SUCCESS = 1;
    public static final Integer STATUS_PROVED_FALSE = 2;

}
