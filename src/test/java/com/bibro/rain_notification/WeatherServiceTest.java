package com.bibro.rain_notification;

import com.bibro.rain_notification.domain.user.AppUser;
import com.bibro.rain_notification.domain.weather.Weather;
import com.bibro.rain_notification.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testNotifyAll() {

    }
}
