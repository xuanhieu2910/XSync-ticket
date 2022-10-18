package compedia.vn.tickmi.mail.utils;

public class DbConstant {

    // Flat to run job
    public  static boolean IS_FLAT_RUN_JOB = false;


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

    // Ticket mail his
    // Status gen and sen ticket
    public static final Integer STATUS_PROVED_NEW = -1;
    public static final Integer STATUS_PROVED_PROCESS = 0;
    public static final Integer STATUS_PROVED_SUCCESS = 1;
    public static final Integer STATUS_PROVED_FALSE = 2;

}
