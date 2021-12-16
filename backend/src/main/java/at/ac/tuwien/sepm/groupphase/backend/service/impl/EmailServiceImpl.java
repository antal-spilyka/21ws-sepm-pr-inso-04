package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void sendEmail(String to, String subject, String text) {
        LOGGER.info("sendEmail to: {}", to);

        if (to.contains("@email.com")) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sepmticketline1@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
