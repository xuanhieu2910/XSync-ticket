package compedia.vn.tickmi.mail.utils;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;


@Log4j2
public class TemplateEmailUtils {

    @Value("vn.cpa.size.mod")
    private static int mod;

    public static String replaceTemplateTicket(String contentHtml, String htmlReplace, String[] pathQR) {
        log.info("Start to replace template ticket!");
        int quantity = pathQR.length + 1;
        StringBuilder htmlOutPut = new StringBuilder();
        if (quantity < mod) {
            Document document = Jsoup.parse(contentHtml);
            Element element = document.getElementById("QR_LOCATE");
            String childHtml = element.html();
            for (int i = 0; i < quantity; i++) {
                String strReplace = childHtml;
                strReplace = strReplace.replace("{QR_HERE}", pathQR[i]);
                htmlOutPut.append(strReplace);
            }
            element.html(htmlOutPut.toString());
        } else {
            int loop = quantity / mod;
            int remainder = quantity % mod;
            if (remainder != 0) {
                ++loop;
            }
            int stt = 0;
            for (int i = 0; i < loop; ++i) {
                StringBuilder tmpReplace = new StringBuilder();
                Document document = Jsoup.parse(contentHtml);
                Element elementTmp = document.getElementById("QR_LOCATE");
                String content = elementTmp.html();
                int loop_2 = mod;
                if (i == loop - 1) {
                    loop_2 = quantity - remainder * mod;
                }
                for (int j = 0; j < loop_2; ++j) {
                    String strReplace = content;
                    strReplace = replaceQR(content, pathQR[stt]);
                    tmpReplace.append(strReplace);
                    ++stt;
                }
                elementTmp.html(tmpReplace.toString());
                htmlOutPut.append(elementTmp.toString());
            }
        }
        htmlReplace.replace("{QR_HERE}", htmlOutPut.toString());
        log.info("End replace template ticket");
        return htmlReplace;
    }

    public static String replaceQR(String htmlContent, String pathQr) {
        return htmlContent.replace("{QR_HERE}", pathQr);
    }

}
