package compedia.vn.tickmi_mail.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TemplateEmailUtils {

    private final static Logger logger = LoggerFactory.getLogger(TemplateEmailUtils.class);

    public static String TEMPLATE_REPLACE = "\"https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/QR_code_for_mobile_English_Wikipedia.svg/2048px-QR_code_for_mobile_English_Wikipedia.svg.png\"";
    public static String TAG_IMAGE_TEMPLATE = "<img id=\"QR_HERE\" src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/QR_code_for_mobile_English_Wikipedia.svg/2048px-QR_code_for_mobile_English_Wikipedia.svg.png\" style=\"width:100%\"></img>";

    public static String replaceTemplateEmail (List<String> pathQrs) {
        String result = "";
        String tmp = TAG_IMAGE_TEMPLATE;
        for (int i = 0 ; i < pathQrs.size(); i++) {
            String replacePath = tmp.replace(TEMPLATE_REPLACE,pathQrs.get(i));
            result+= replacePath;
        }
        return result;
    }

    public static String replaceTag (String root,String newStr) {
        String a = root.replace(TAG_IMAGE_TEMPLATE,newStr);
        return a;
    }
}
