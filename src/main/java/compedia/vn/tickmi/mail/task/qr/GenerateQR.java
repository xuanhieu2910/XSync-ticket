package compedia.vn.tickmi.mail.task.qr;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import compedia.vn.tickmi.mail.utils.DbConstant;
import compedia.vn.tickmi.mail.utils.GenerateUtils;
import compedia.vn.tickmi.mail.utils.PropertiesUtil;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class GenerateQR {

    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("DDMMYYYY");
    private final static String SEPARATOR =  File.separator;


    public static String handlerGeneratePathQR(String ticketEventCode, Long eventId, String nameTicket,
                                               int flatLogo, int flatName, String pathLogo){
        log.info("Event code : " + ticketEventCode + " ,name ticket: " + nameTicket);
        String pathQR = null;
        String root = PropertiesUtil.getProperty("vn.cpa.static.location.upload.gen_qr");
        String filePathOutPut = PropertiesUtil.getProperty("vn.cpa.static.location.export.qr");
        String todayFolder = SIMPLE_DATE_FORMAT.format(new Date());
        log.info("root: " + root + " - file path out put: " + filePathOutPut);
        String filePathQrGen = root + SEPARATOR + eventId + SEPARATOR + todayFolder;
        Path nameFolder =  Paths.get(filePathQrGen);
        if (Files.notExists(nameFolder)) {
            try {
                Files.createDirectories(nameFolder);
                log.info("Create folder: " + filePathQrGen + " success!");
            } catch (IOException e) {
                log.error("Can't not create folder {}", filePathQrGen);
            }
        }
        filePathQrGen = filePathQrGen + SEPARATOR + nameTicket + "." + DbConstant.EXTENSION_GENERATE_QR[0];
        pathQR = filePathOutPut + SEPARATOR + eventId + SEPARATOR + todayFolder + SEPARATOR +
                nameTicket + "." + DbConstant.EXTENSION_GENERATE_QR[0];
        log.info("PATH_RETURN : " + pathQR);
        log.debug("Create file success");
        handleImageGenerateQR(filePathQrGen, ticketEventCode, nameTicket,eventId,flatLogo,flatName,pathLogo);
        return pathQR;
    }


    /**
     * @param pathQR This have been generate from method handlerGeneratePathQR(params...)
     */
    public static void handleImageGenerateQR(String pathQR, String codeTicketEvent, String guestName,Long eventId,
                                             int flatLogo, int flatName, String pathLogo) {
        try {
            log.info("---------------------------- GENERATE QR ----------------------");
            Map<EncodeHintType, Object> encodeHintTypeObjectMap = new HashMap<>();
            encodeHintTypeObjectMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            QRCode code = Encoder.encode(codeTicketEvent+"\\"+eventId, ErrorCorrectionLevel.H, encodeHintTypeObjectMap);
            BufferedImage image = renderQRImage(code, DbConstant.WIDTH_QR, DbConstant.HEIGHT_QR, DbConstant.PADDING_QR,
                    guestName,flatLogo,flatName, pathLogo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, DbConstant.EXTENSION_GENERATE_QR[0], baos);
            byte[] bytes = baos.toByteArray();
            FileOutputStream stream = new FileOutputStream(pathQR);
            stream.write(bytes);
            log.info("--------------------------- GENERATE FINISHED ----------------------");
        } catch (WriterException | IOException e) {
            log.error("Error generate QR ", e);
        }
    }


    /**
     * @param code : Content of QR Code ( Matrix 2D to fill style for it)
     * @param width : Size width for sub matrix
     * @param height: Size height for sub matrix
     * @param quietZone : Padding of QR
     * @param nameTicket : Name  ticket is display bottom of QR Code
     * @param flatLogo : Flat to enable/disable to display logo of Organization
     * @param flatName : Flat to enable/disable to display name Ticket.
     * */
    private static BufferedImage renderQRImage(QRCode code, int width, int height, int quietZone, String nameTicket,
                                               int flatLogo, int flatName, String pathLogo) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setBackground(Color.white);
        graphics2D.clearRect(0, 0, width, height);
        graphics2D.setColor(Color.BLACK);

        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + (quietZone * 2);
        int qrHeight = inputHeight + (quietZone * 2);
        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        final int FINDER_PATTERN_SIZE = 7;
        final float CIRCLE_SCALE_DOWN_FACTOR = 1f;
        int circleSize = (int) (multiple * CIRCLE_SCALE_DOWN_FACTOR);


        for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
            for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                if (input.get(inputX, inputY) == 1) {
                    if (!(inputX <= FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX >= inputWidth - FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX <= FINDER_PATTERN_SIZE && inputY >= inputHeight - FINDER_PATTERN_SIZE)) {
                        graphics2D.fillRect(outputX, outputY, circleSize, circleSize);
                    }
                }
            }
        }

        int circleDiameter = multiple * FINDER_PATTERN_SIZE;
        drawFinderPatternCircleStyle(graphics2D, leftPadding, topPadding, circleDiameter);
        drawFinderPatternCircleStyle(graphics2D, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, circleDiameter);
        drawFinderPatternCircleStyle(graphics2D, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, circleDiameter);

        // Draw logo Ticket
        if (flatLogo == DbConstant.IS_FLAT_DISPLAY_LOGO && null != pathLogo) {
            BufferedImage logoImage = getLogoTicket(pathLogo);
            int visitedWith = DbConstant.WIDTH_QR / 2 - DbConstant.WIDTH_LOGO/2;
            int visitedHeight = DbConstant.HEIGHT_QR / 2 - DbConstant.HEIGHT_LOGO/2;
            graphics2D.drawImage(logoImage,visitedWith,visitedHeight,DbConstant.WIDTH_LOGO, DbConstant.HEIGHT_LOGO,null);
        }
        // Draw name Ticket
        if (flatName == DbConstant.IS_FLAT_DISPLAY_NAME_TICKET) {
            graphics2D.drawString(nameTicket, outputWidth / 2 - DbConstant.POSITION_NAME, outputHeight - DbConstant.PADDING_QR * 3);
        }
        return image;
    }

    private static void drawFinderPatternCircleStyle(Graphics2D graphics, int x, int y, int circleDiameter) {
        final int WHITE_CIRCLE_DIAMETER = circleDiameter * 5 / 7;
        final int WHITE_CIRCLE_OFFSET = circleDiameter / 7;
        final int MIDDLE_DOT_DIAMETER = circleDiameter * 3 / 7;
        final int MIDDLE_DOT_OFFSET = circleDiameter * 2 / 7;
        graphics.setColor(Color.BLACK);
        graphics.fillRect(x, y, circleDiameter, circleDiameter);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x + WHITE_CIRCLE_OFFSET, y + WHITE_CIRCLE_OFFSET, WHITE_CIRCLE_DIAMETER, WHITE_CIRCLE_DIAMETER);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(x + MIDDLE_DOT_OFFSET, y + MIDDLE_DOT_OFFSET, MIDDLE_DOT_DIAMETER, MIDDLE_DOT_DIAMETER);
    }

    private  static BufferedImage getLogoTicket (String pathLogo) throws IOException {
        log.info(DbConstant.URL + pathLogo);
        BufferedImage originalImage = ImageIO.read(new File(DbConstant.URL + pathLogo));
        BufferedImage newResizedImage = new BufferedImage(DbConstant.WIDTH_LOGO, DbConstant.HEIGHT_LOGO, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newResizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.fillRect(0, 0, DbConstant.WIDTH_LOGO, DbConstant.HEIGHT_LOGO);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(originalImage, 0, 0, DbConstant.WIDTH_LOGO, DbConstant.HEIGHT_LOGO, null);
        return newResizedImage;
    }
}
