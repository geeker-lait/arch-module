package org.arch.alarm.biz.notifier.mail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.pojo.ToEmail;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/11/2021 9:06 AM
 */
@Slf4j
@Service
@AllArgsConstructor
public class MailService {

    /**
     * JavaMailSender是Spring Boot在MailSenderPropertiesConfiguration 类中配直好的，该类在 Mail
     * 自动配置类 MailSenderAutoConfiguration 中导入 因此这里注入 JavaMailSender 就可以使用了
     */
    private JavaMailSender mailSender;

    /**
     * 动态切换邮件账号
     *
     * @param host
     * @param userName
     * @param pwd
     * @return
     */
    public void changeMailAccount(String host, String userName, String pwd) {
        JavaMailSenderImpl sender = (JavaMailSenderImpl) mailSender;
        //BeanUtils.copyProperties(mailSender,sender);
        sender.setHost(host);
        sender.setUsername(userName);
        sender.setPassword(pwd);
    }

    /**
     * 1、发送普通文本邮件
     *
     * @param toEmail
     */
    public void sendSimpleMail(ToEmail toEmail) {
        try {
            // 多个收件人之间用英文逗号分隔
            //String[] mailToArr = mailTo.split(",");
            for (String address : toEmail.getMailTos()) {
                // 简单邮件信息类
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                // HTML邮件信息类
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
                // 昵称
                mimeMessageHelper.setFrom(new InternetAddress(toEmail.getMailFromNick() + " <" + toEmail.getMailFrom() + ">"));
                mimeMessageHelper.setTo(address);
                if (toEmail.getCcs().size() != 0) {
                    String[] cca = new String[toEmail.getCcs().size()];
                    mimeMessageHelper.setCc(cca);
                }
                mimeMessageHelper.setSubject(toEmail.getSubject());
                mimeMessageHelper.setText(toEmail.getContent());
                mailSender.send(mimeMessage);
            }
        } catch (Exception e) {
            log.error("发送邮件失败：", e);
        }
    }

    /**
     * 2、发送带附件的邮件
     *
     * @param toEmail
     * @param files
     */
    public void sendMailWithAttachments(ToEmail toEmail, List<File> files) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            /*
            第二个参数true表示构造一个multipart message类型的邮件，multipart message类型的邮件包含多个正文、附件以及内嵌资源，
            邮件的表现形式更丰富
             */
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(new InternetAddress(toEmail.getMailFromNick() + " <" + toEmail.getMailFrom() + ">"));
            mimeMessageHelper.setSubject(toEmail.getSubject());
            mimeMessageHelper.setText(toEmail.getContent());
            // 设置多个收件人
            String[] toAddress = (String[]) toEmail.getMailTos().toArray();
            mimeMessageHelper.setTo(toAddress);
            if (toEmail.getCcs().size() != 0) {
                String[] cca = new String[toEmail.getCcs().size()];
                mimeMessageHelper.setCc(cca);
            }
            // 添加附件
            if (null != files) {
                for (File file : files) {
                    // 通过addAttachment方法添加附件
                    mimeMessageHelper.addAttachment(file.getName(), file);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //发送邮件
        mailSender.send(mimeMessage);

    }

    /**
     * 3、正文内容带图片
     *
     * @param toEmail
     * @param imagePaths
     * @param imageId
     */

    public void sendMailWithImage(ToEmail toEmail, String[] imagePaths, String[] imageId) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(new InternetAddress(toEmail.getMailFromNick() + " <" + toEmail.getMailFrom() + ">"));
            // 设置多个收件人
            String[] toAddress = (String[]) toEmail.getMailTos().toArray();
            mimeMessageHelper.setTo(toAddress);
            if (toEmail.getCcs().size() != 0) {
                String[] cca = new String[toEmail.getCcs().size()];
                mimeMessageHelper.setCc(cca);
            }
            mimeMessageHelper.setSubject(toEmail.getSubject());
            // 第二个参数为true表示邮件正文是html格式的，默认是false
            mimeMessageHelper.setText(toEmail.getContent(), true);
            // 添加图片
            if (imagePaths != null && imagePaths.length != 0) {
                for (int i = 0; i < imagePaths.length; i++) {
                    // 通过FileSystemResource构造静态资源
                    FileSystemResource fileSystemResource = new FileSystemResource(imagePaths[i]);
                    // 调用addInline方法将资源加入邮件对象中
                    mimeMessageHelper.addInline(imageId[i], fileSystemResource);
                }
            }

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.out.println(e);
        }
    }

    /**
     * 4、使用Themeleaf构建邮件模板。需额外加spring-boot-starter-thymeleaf依赖
     *
     * @param toEmail
     */
    public void sendHtmlMailThymeLeaf(ToEmail toEmail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(new InternetAddress(toEmail.getMailFromNick() + " <" + toEmail.getMailFrom() + ">"));
            // 设置多个收件人
            String[] toAddress = (String[]) toEmail.getMailTos().toArray();
            mimeMessageHelper.setTo(toAddress);
            if (toEmail.getCcs().size() != 0) {
                String[] cca = new String[toEmail.getCcs().size()];
                mimeMessageHelper.setCc(cca);
            }
            mimeMessageHelper.setSubject(toEmail.getSubject());
            // 第二个参数为true表示邮件正文是html格式的，默认是false
            mimeMessageHelper.setText(toEmail.getContent(), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println(e);
        }
    }
}
