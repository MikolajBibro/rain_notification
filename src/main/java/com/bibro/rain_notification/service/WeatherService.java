package com.bibro.rain_notification.service;

import com.bibro.rain_notification.domain.user.AppUser;
import com.bibro.rain_notification.domain.user.Location;
import com.bibro.rain_notification.domain.weather.Weather;
import com.bibro.rain_notification.domain.weather.WeatherApi;
import com.bibro.rain_notification.repository.LocationRepository;
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
    private LocationRepository locationRepository;

    public WeatherService(WeatherApi weatherApi, EmailService emailService, UserService userService, LocationRepository locationRepository) {
        this.weatherApi = weatherApi;
        this.emailService = emailService;
        this.userService = userService;
        this.locationRepository = locationRepository;
    }

    private void notifyByLocation(Location location, AppUser user) throws IOException {
        Weather weather = weatherApi.getWeather(location);

        if (shouldSendNotification(location)) {
            emailService.sendNotification(user, weather);
        }
    }

    private void setLastRainyWeatherForUserLocation(Location location) {
        if (weatherApi.getWeather(location).isRainy()) {
            location.setLastRainyWeather(LocalDateTime.now());
            locationRepository.save(location);
        }
    }

    private boolean shouldSendNotification(Location location) {
        return weatherApi.getWeather(location).isRainy() && (location.getLastRainyWeather() == null || location.getLastRainyWeather().isBefore(LocalDateTime.now().minusHours(1)));
    }

    @Scheduled(fixedDelay = 15 * 60 * 1000)
    public void notifyUsers() {
        List<AppUser> users = userService.findAll();

        users.forEach(appUser -> appUser.getLocations()
                .forEach(location -> {
                    try {
                        notifyByLocation(location, appUser);
                        setLastRainyWeatherForUserLocation(location);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
    }
}
