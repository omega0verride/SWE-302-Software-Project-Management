package com.redscooter.config;

import com.redscooter.config.configProperties.EmailConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    private final EmailConfigProperties emailConfigProperties;

    @Autowired
    public EmailConfig(EmailConfigProperties emailConfigProperties) {
        this.emailConfigProperties = emailConfigProperties;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfigProperties.getHost());
        mailSender.setPort(emailConfigProperties.getPort());

        System.out.println("------->"+emailConfigProperties.getUsername());
        mailSender.setUsername(emailConfigProperties.getUsername());
        mailSender.setPassword(emailConfigProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", emailConfigProperties.getProtocol());
        props.put("mail.smtp.auth", emailConfigProperties.isAuth());
        props.put("mail.smtp.starttls.enable", emailConfigProperties.isStarttls());
        props.put("mail.debug", emailConfigProperties.isDebug());

        return mailSender;
    }
}

