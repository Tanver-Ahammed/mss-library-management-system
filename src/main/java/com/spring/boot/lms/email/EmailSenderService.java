package com.spring.boot.lms.email;

import com.spring.boot.lms.config.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailWithoutAttachment(String toEmail,
                                           String subjectEmail,
                                           String bodyEmail) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMailMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);

        mimeMessageHelper.setFrom(AppConstants.fromEmail, "ICT Library, MBSTU");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subjectEmail);
        mimeMessageHelper.setText(bodyEmail, true);

        this.javaMailSender.send(mimeMailMessage);

        System.out.println("Mail send successfully.....");

    }

    public void sendEmailWithAttachment(String toEmail,
                                        String subjectEmail,
                                        String bodyEmail,
                                        String[] attachment) throws MessagingException {

        MimeMessage mimeMailMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);

        mimeMessageHelper.setFrom(AppConstants.fromEmail);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subjectEmail);
        mimeMessageHelper.setText(bodyEmail);

        File file = new File("/home/tanver/Documents/Spring/Personal Project/" +
                "mss-library-management-system/images/Tanver+.jpgb409d92c-56ca-4324-aacd-7119f570106e.jpg");

        mimeMessageHelper.addAttachment(file.getName(), file);

        this.javaMailSender.send(mimeMailMessage);

        System.out.println("Mail send successfully.....");

    }


}
