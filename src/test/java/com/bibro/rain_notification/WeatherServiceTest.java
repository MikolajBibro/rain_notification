package com.bibro.rain_notification;

import com.bibro.rain_notification.domain.user.AppUser;
import com.bibro.rain_notification.domain.user.Location;
import com.bibro.rain_notification.domain.weather.Weather;
import com.bibro.rain_notification.domain.weather.WeatherApi;
import com.bibro.rain_notification.repository.LocationRepository;
import com.bibro.rain_notification.service.EmailService;
import com.bibro.rain_notification.service.UserService;
import com.bibro.rain_notification.service.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private UserService userService;

    @Mock
    private WeatherApi weatherApi;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private WeatherService weatherService;

    @Before
    public void setUp() {

    }

    @Test
    public void givenBadWeather_whenCallingNotifyByLocation_thenCallSendNotification() {
        //given
        AppUser user = new AppUser("username", "password");
        List<AppUser> users = new ArrayList<>();
        users.add(user);

        Location location = new Location("longitude", "latitude");
        location.setLastRainyWeather(LocalDateTime.now().minusHours(1));
        List<Location> locations = new ArrayList<>();
        locations.add(location);

        user.setLocations(locations);

        when(userService.findAll()).thenReturn(users);
        when(weatherApi.getWeather(location)).thenReturn(Weather.HEAVY);

        //when
        weatherService.notifyUsers();

        //then
        verify(emailService, times(1)).sendNotification(any(AppUser.class), eq(Weather.HEAVY));
    }
}
