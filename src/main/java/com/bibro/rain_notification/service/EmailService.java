package com.bibro.rain_notification.service;

import com.bibro.rain_notification.domain.user.AppUser;
import com.bibro.rain_notification.domain.weather.Weather;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(AppUser user, Weather weather) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Weather notification");
        message.setText("Weather has changed in your location. Current weather - " + weather.getDescription());
        mailSender.send(message);
    }
}
