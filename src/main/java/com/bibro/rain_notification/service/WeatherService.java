package com.bibro.rain_notification.service;

import com.bibro.rain_notification.domain.user.AppUser;
import com.bibro.rain_notification.domain.user.Location;
import com.bibro.rain_notification.domain.weather.Weather;
import com.bibro.rain_notification.domain.weather.WeatherApi;
import com.bibro.rain_notification.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class WeatherService {

    private WeatherApi weatherApi;
    private EmailService emailService;
    private UserService userService;

    public WeatherService(WeatherApi weatherApi, EmailService emailService, UserService userService) {
        this.weatherApi = weatherApi;
        this.emailService = emailService;
        this.userService = userService;
    }

    private void notifyByLocation(Location location, AppUser user) throws IOException {
        Weather weather = weatherApi.getWeather(location);

        if (shouldSendNotification(location, user)) {
            emailService.sendNotification(user, weather);
        }
    }

    private void setLastRainyWeatherForUserLocation(Location location, AppUser user) {
        if (weatherApi.getWeather(location).isRainy()) userService.setLastRainyWeatherForNow(user);
    }

    private boolean shouldSendNotification(Location location, AppUser user) {
        return weatherApi.getWeather(location).isRainy() && (user.getLastRainyWeather() == null || user.getLastRainyWeather().isBefore(LocalDateTime.now().minusHours(1)));
    }

    @Scheduled(fixedDelay = 15 * 60 * 1000)
    public void notifyUsers() {
        List<AppUser> users = userService.findAll();

        users.forEach(appUser -> appUser.getLocations()
                .forEach(location -> {
                    try {
                        notifyByLocation(location, appUser);
                        setLastRainyWeatherForUserLocation(location, appUser);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
    }
}
