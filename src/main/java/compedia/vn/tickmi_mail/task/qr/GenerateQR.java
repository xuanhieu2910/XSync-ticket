package compedia.vn.tickmi_mail.task.qr;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import compedia.vn.tickmi_mail.utils.DbConstant;
import compedia.vn.tickmi_mail.utils.FilesUtils;
import lombok.extern.log4j.Log4j2;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Log4j2
public class GenerateQR {

    public static String handlerGeneratePathQR (Long eventId, Long tickEventId, Long guestId, Integer countTicket) {
        return FilesUtils.createFilePathQR(eventId, tickEventId, guestId, countTicket, DbConstant.EXTENSION_GENERATE_QR[0]);
    }


    /**
     *  @param content Code ticket
     *  @param pathQR This have been generate from method handlerGeneratePathQR(params...)
     *  @param  nameTicket : index of ticket in Guest
     *
     * */
    public static void handleImageGenerateQR (String content, String pathQR, String nameTicket) {
        try {
            log.info("---------------------------- GENERATE QR ----------------------");
            log.info("Content :" + content + ", pathQR: " + pathQR + ", nameTicket:" + nameTicket);
            Map<EncodeHintType, Object> encodeHintTypeObjectMap = new HashMap<>();
            encodeHintTypeObjectMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            QRCode code = Encoder.encode(content, ErrorCorrectionLevel.H, encodeHintTypeObjectMap);
            BufferedImage image = renderQRImage(code, DbConstant.WIDTH_QR, DbConstant.HEIGHT_QR, DbConstant.PADDING_QR,nameTicket);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, DbConstant.EXTENSION_GENERATE_QR[0], baos);
            byte[] bytes = baos.toByteArray();
            FileOutputStream stream = new FileOutputStream(pathQR);
            stream.write(bytes);
            log.info("---------------------------GENERATE FINISHED----------------------");
        } catch (WriterException | IOException e) {
            log.error("Error generate QR ",e);
        }
    }


    private static BufferedImage renderQRImage(QRCode code, int width, int height, int quietZone, String nameTicket) {
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
        graphics2D.drawString(nameTicket,outputWidth/2 - DbConstant.POSITION_NAME, outputHeight - DbConstant.PADDING_QR*3);
        return image;
    }

    private  static void drawFinderPatternCircleStyle(Graphics2D graphics, int x, int y, int circleDiameter) {
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



}
