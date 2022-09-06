package compedia.vn.tickmi_mail.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FilesUtils {

    private final static Logger logger = LoggerFactory.getLogger(FilesUtils.class);


    public static String pathReturn = "";
    public final static String EXT_PDF = "pdf";
    private static final String SEPARATOR = "/";
    public static final String FOLDER_NAME_EXPORT = "export";
    private static final String FOLDER_NAME_PARENT = "resources";
    private static final String FOLDER_NAME_IMAGE = "upload_image";
    private static final String FOLDER_NAME_FILE = "upload_file";
    public final static String EXT_OFFICE = "xls,xlsx,doc,docx,ppt";
    private static final String FOLDER_NAME_CREATE_IMAGE_TICKET = "upload_create_image";
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("DDMMYYYY");

    // Save file if success then return file path, else return null
    public static Map<String, String> saveFiles(MultipartFile[] uploadedFile) throws IOException {
        Map<String, String> outputList = new LinkedHashMap<>();
        int i = 0;
        for (MultipartFile multipartFile : uploadedFile) {
            if (multipartFile.isEmpty()) {
                return new LinkedHashMap<>();
            }
            String mimeType = multipartFile.getContentType().split("/")[0];
            String path = "image".equalsIgnoreCase(mimeType) ? saveImage(multipartFile) : saveFile(multipartFile);
            outputList.put(path, multipartFile.getOriginalFilename());
        }
        return outputList;
    }

    // Save file if success then return file path, else return null
    public static String saveFile(MultipartFile uploadedFile) {
        return save(uploadedFile, FOLDER_NAME_FILE);
    }

    // Save file if success then return file path, else return null
    public static String saveImage(MultipartFile uploadedFile) {
        return save(uploadedFile, FOLDER_NAME_IMAGE);
    }

    public static String saveImageCreateTicket(MultipartFile uploadedFile) {
        return save(uploadedFile, FOLDER_NAME_CREATE_IMAGE_TICKET);
    }

    private static String save(MultipartFile uploadedFile, String folderName) {
        try {
            return save(uploadedFile.getInputStream(), uploadedFile.getOriginalFilename(), folderName);
        } catch (IOException e) {
            logger.error("Save file error", e);
            return null;
        }
    }

    private static String save(InputStream inputStream, String fileName, String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();
        String folder = buildFolderUpload(folderName);
        File inFiles = new File(folder);
        if (!inFiles.exists() && !inFiles.mkdirs()) {
            logger.error("Can't create folder");
        }
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(fileName));
        String dir = PropertiesUtil.getProperty("vn.compedia.static.location") + "/" + folderName + "/" + todayFolder + "/" + fileId;
        try {
            if (file.exists()) {
                file.delete();
            }
            FileUtils.copyInputStreamToFile(inputStream, file);
            if (StringUtils.isNotBlank(fileName) && isAcceptFileTypeAudio(fileName)) {
                String cmd = "ffmpeg -i " + dir + "." + FilenameUtils.getExtension(fileName) + " -vn -ar 44100 -ac 1 -b:a 32k -f mp3 " + dir + ".mp3";
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                    cmd = "cmd /c " + cmd;
                }
                Process p = Runtime.getRuntime().exec(cmd);
                int exit = p.waitFor();
                Thread t = new Thread(() -> {
                    try {
                        if (exit == 0) {
                            Thread.sleep(1000);
                        } else {
                            Thread.sleep(exit);
                        }
                        file.delete();
                        System.out.println("success");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                t.start();
                return SEPARATOR + folderName + SEPARATOR + todayFolder + SEPARATOR + fileId + ".mp3";
            }
            return SEPARATOR + folderName + SEPARATOR + todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(fileName);
        } catch (IOException | InterruptedException e) {
            logger.error("Save file error", e);
            return null;
        }
    }

    public static FileInputStream getInputStream(String filePath) {
        try {
            return new FileInputStream(new File(filePath));
        } catch (FileNotFoundException ignored) {

        }
        return null;
    }

    public static String getFilePathFromDatabase(String databaseFilePath) {
        return SEPARATOR + FOLDER_NAME_PARENT + SEPARATOR + databaseFilePath;
    }

    // Get only file name
    public static String getFilenameFromFilePath(String databaseFilePath) {
        if (StringUtils.isBlank(databaseFilePath)) {
            return "";
        }
        return databaseFilePath.substring(databaseFilePath.lastIndexOf(SEPARATOR) + 1);
    }

    // Create file id (unique)
    private static String generateFileId() {
        return DateUtil.getCurrentDateStr() + RandomStringUtils.randomAlphanumeric(16);
    }

    private static String buildFolderUpload(String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String folderSave = PropertiesUtil.getProperty("vn.compedia.static.location");

        // In project
        return folderSave
                + File.separator + folderName
                + File.separator + todayFolder
                + File.separator;
    }

    public static String getFileExtFromFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static boolean isPdfFileExt(String fileName) {
        return StringUtils.equalsIgnoreCase(getFileExtFromFileName(fileName), EXT_PDF);
    }

    public static boolean isOfficeFileExt(String fileName) {
        return StringUtils.contains(EXT_OFFICE, getFileExtFromFileName(fileName).toLowerCase());
    }

    public static boolean isAcceptFileTypeAudio(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_file_audio_must_convert"));
    }

    public static boolean isAcceptFileTypeVideo(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_file_types_video"));
    }

    public static boolean isAcceptFileType(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_file_types"));
    }

    public static boolean isAcceptAudioFileType(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_file_types_audio"));
    }


    public static boolean isAcceptFileTypeImage(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_image_file_types"));
    }

    public static boolean isAcceptFileType(String fileName, String acceptTypes) {
        if (StringUtils.isBlank(acceptTypes) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(acceptTypes.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName).toLowerCase());
    }

    // PDF
    public static boolean isAcceptFilePDFType(String fileName) {
        String fileTypeString = getAcceptFilePDFString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static String getAcceptFilePDFString() {
        return PropertiesUtil.getProperty("accept_file_types_pdf");
    }

    public static void deleteFileByListPath(List<String> fileList) {
        for (String filePath : fileList) {
            File file = new File(filePath);
            if (file.exists()) {
                boolean a = file.delete();
                System.out.println(a);
            }
        }
    }



    // Create folder upload qr
    public static String createFilePathQR(Integer eventId, Integer ticketEventId, Integer guestId, int index, String extension) {
        String root = PropertiesUtil.getProperty("vn.cpa.static.location.upload.gen_qr");
        String todayFolder = SIMPLE_DATE_FORMAT.format(new Date());
        String filePathOutPut = PropertiesUtil.getProperty("vn.cpa.static.location.export.qr");
        String filePathQrGen = root + File.separator + todayFolder + File.separator + eventId;
        File file = new File(filePathQrGen);
        if (!file.exists() && !file.mkdirs()) {
            logger.error("Can't create folder");
        } else {
            filePathQrGen = filePathQrGen + File.separator + guestId + "_" + ticketEventId + "_" + index + "." + extension;
            filePathOutPut = File.separator + todayFolder + File.separator + eventId + File.separator + guestId + "_" + ticketEventId + "_" + index + "." + extension;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(filePathQrGen);
                logger.debug("Create file success");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return filePathOutPut;
    }

    public static String getFolderInfo() {
        StringBuilder builder = new StringBuilder();
        builder.setLength(0);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        builder.append(year).append(month).append(day);
        return builder.toString();
    }

}
