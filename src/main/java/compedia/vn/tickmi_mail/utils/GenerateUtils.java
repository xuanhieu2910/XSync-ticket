package compedia.vn.tickmi_mail.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class GenerateUtils {

    private final static Logger logger = LoggerFactory.getLogger(GenerateUtils.class);

    public static String generateCodeTicket() {
        logger.debug("Generate code ticket");
        return UUID.randomUUID().toString();
    }

}
