//package compedia.vn.tickmi_mail.task.mail;
//
//
//import compedia.vn.tickmi_mail.dto.CustomerEmailDto;
//import compedia.vn.tickmi_mail.dto.InformationMailDto;
//import compedia.vn.tickmi_mail.entity.MailDetailHis;
//import compedia.vn.tickmi_mail.entity.MailRoot;
//import compedia.vn.tickmi_mail.service.MailDetailHisService;
//import compedia.vn.tickmi_mail.service.MailRootService;
//import compedia.vn.tickmi_mail.utils.DbConstant;
//import compedia.vn.tickmi_mail.utils.MailUtils;
//import compedia.vn.tickmi_mail.utils.PropertiesUtil;
//import compedia.vn.tickmi_mail.utils.TemplateEmailUtils;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.*;
//import java.util.concurrent.BlockingQueue;
//
//@Component
//@EnableScheduling
//@EnableAsync
//@Log4j2
//public class HandleMailService {
//
//
//    private static Queue<MailRoot> queueRootMail = new ArrayDeque<>();
//
//
//    @Autowired
//    MailRootService mailRootService;
//
//    @Autowired
//    MailDetailHisService detailHisService;
//
//    /**
//     *  1. Process get root mail
//     *
//     * */
//    @Async
//    @Scheduled(fixedRate = 2000)
//    public void processGetRootMail () {
//        try {
//            List<MailRoot> mailRootList = mailRootService.findAllMailRoot(DbConstant.MAIL_ROOT_STATUS_NEW, DbConstant.MAX_RETRY, DbConstant.SIZE_LIMIT);
//            if (!CollectionUtils.isEmpty(mailRootList)) {
//                mailRootList.stream().forEach(x->x.setStatus(DbConstant.MAIL_ROOT_STATUS_SEND));
//                mailRootService.updateMailRoots(mailRootList);
//                queueRootMail.addAll(mailRootList);
//            } else {
//                log.info("Mail root is empty!");
//            }
//        }catch (Exception e) {
//            log.error("Error to process get root mail",e);
//        }
//    }
//
//    /**
//     *
//     * 2. Process generate content to send email
//     *
//     * */
//    @Scheduled(fixedDelay = 1000)
//    public void processGenerateContentToSendEmail () {
//        if (!queueRootMail.isEmpty()) {
//            MailRoot mailRoot = queueRootMail.poll();
//            try {
//            // Get information
//            Optional<InformationMailDto> dto = mailRootService.getInformationMailDtoByGuestId(mailRoot.getGuestId());
//            if (!dto.isPresent()) {
//                log.error("Error information mail dto empty!" );
//            }
//            else {
//                if (mailRoot.getRetry().equals(DbConstant.MAX_RETRY_DETAIL)) {
//                    createMailHis(dto.get(),DbConstant.MAIL_HIS_STATUS_FALSE);
//                    // remove mail root
//                    mailRootService.deleteMailRoot(mailRoot);
//                } else {
//                    // success
//                    if (dto.isPresent()) {
//                        String replaceTmp = TemplateEmailUtils.replaceTemplateEmail(dto.get().getPathQr());
//                        String result = TemplateEmailUtils.replaceTag(dto.get().getHtml(), replaceTmp);
//                        dto.get().setContent(result);
//                        // send email
//                        sendEmail(dto.get());
//                        // remove mail root
//                        //TODO: Comment tam de test
////                        mailRootService.deleteMailRoot(mailRoot);
//                    }
//                }
//            }
//            } catch (IOException e) {
//                // false
//                mailRoot.setRetry(mailRoot.getRetry() + 1);
//                mailRoot.setStatus(DbConstant.MAIL_ROOT_STATUS_NEW);
//                mailRootService.updateMailRoot(mailRoot);
//                log.error("Error process generate content to send email",e);
//            } catch (SQLException e) {
//                log.error("Error process generate content to send email", e);
//            }
//        }
//    }
//
//
//    /**
//     *
//     * 3. Process send email
//     * */
//    public void sendEmail (InformationMailDto mailDto){
//        CustomerEmailDto customerEmailDto = null;
//        if (mailDto.getEmailUser() != null) {
//            customerEmailDto = new CustomerEmailDto();
//            customerEmailDto.setUser(mailDto.getEmailUser());
//            customerEmailDto.setPassword(mailDto.getEmailPassword());
//            customerEmailDto.setHost(mailDto.getEmailHost());
//            customerEmailDto.setPort(mailDto.getEmailPort());
//        }
//        try {
//            MailUtils.getInstance().sendTicketEmail(mailDto.getEmailTo(), mailDto.getContent(), mailDto.getGuestName(), customerEmailDto);
//            createMailHis(mailDto,DbConstant.MAIL_HIS_STATUS_SUCCESS);
//            // remove
//        }
//        catch (Exception e) {
//            createMailHis(mailDto,DbConstant.MAIL_HIS_STATUS_FALSE);
//        }
//    }
//
//
//
//    private void createMailHis (InformationMailDto mailDto, Integer status) {
//        MailDetailHis mailDetailHis = new MailDetailHis();
//        if (mailDto.getEmailUser() == null) {
//            mailDetailHis.setMailFrom(PropertiesUtil.getEmailProperty("mail.user"));
//        }
//        else {
//            mailDetailHis.setMailFrom(mailDto.getEmailUser());
//        }
//        mailDetailHis.setMailTo(mailDto.getEmailTo());
//        mailDetailHis.setGuestId(mailDto.getGuestId());
//        Date now = new Date();
//        mailDetailHis.setCreateDate(new Timestamp(now.getTime()));
//        mailDetailHis.setUpdateDate(new Timestamp(now.getTime()));
//        mailDetailHis.setStatus(status);
//        detailHisService.updateMailDetailHis(mailDetailHis);
//    }
//}
