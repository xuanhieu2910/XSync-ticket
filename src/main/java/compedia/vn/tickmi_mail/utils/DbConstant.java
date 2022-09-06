package compedia.vn.tickmi_mail.utils;

public class DbConstant {

    // Retry
    public final static Integer INIT_RETRY = 0;

    // Limit size to query db get value
    public static final Integer SIZE_LIMIT = 10;

    //extension qr
    public static final String[] EXTENSION_GENERATE_QR = {"png", "img", "jpg"};

    // STYLE QR
    public static final Integer WIDTH_QR = 400;
    public static final Integer HEIGHT_QR = 400;
    public static final Integer PADDING_QR = 3;
    public static final Integer POSITION_NAME = 33;


    // event request
    public final static Integer MAX_RETRY = 3;
    public final static Integer STATUS_EVENT_REQUEST = 1;
    public final static Integer STATUS_NEW_EVENT_REQUEST = -1;
    public final static Integer STATUS_READY_SEND = 2;
    public final static Integer STATUS_FALSE = 3;
    // event request detail
    public final static Integer MAX_RETRY_DETAIL = 3;
    public final static Integer INIT_RETRY_DETAIL = 0;
    public final static Integer STATUS_EVENT_REQUEST_DETAIL = 1;
    public final static Integer STATUS_EVENT_REQUEST_DETAIL_FLAT = 2;
    public final static Integer STATUS_EVENT_REQUEST_DETAIL_DONE = 3;
    public final static Integer STATUS_NEW_EVENT_REQUEST_DETAIL = -1;
    // Event mail
    public final static Integer EVENT_MAIL_NEW = -1;
    public final static Integer EVENT_MAIL_RETRY_DETAIL = 0;

    // MailRoot
    public final static Integer MAIL_ROOT_STATUS_NEW = -1;
    public final static Integer MAIL_ROOT_STATUS_SEND = 1;
    public final static Integer MAIL_ROOT_LIMIT = 10;

    // Ticket
    public final static Integer TICKET_NOT_CHECKIN = 0;
    public final static Integer TICKET_CHECKIN = 1;

    // Ticket mail his
    public final static Integer MAIL_HIS_STATUS_FALSE = 0;
    public final static Integer MAIL_HIS_STATUS_SUCCESS = 1;



}
