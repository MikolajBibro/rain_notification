package com.bibro.rain_notification.service;

import com.bibro.rain_notification.domain.user.Location;
import com.bibro.rain_notification.domain.user.AppUser;
import com.bibro.rain_notification.domain.weather.Weather;
import com.bibro.rain_notification.domain.weather.WeatherApi;
import com.bibro.rain_notification.repository.UserRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public void createUser(AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addLocation(Location location, AppUser user) {
        user.addLocation(location);
        userRepository.save(user);
    }

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }
}
