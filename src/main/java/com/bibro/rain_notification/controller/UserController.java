package com.bibro.rain_notification.controller;

import com.bibro.rain_notification.domain.user.Location;
import com.bibro.rain_notification.domain.user.AppUser;
import com.bibro.rain_notification.repository.UserRepository;
import com.bibro.rain_notification.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/sign-up")
    public void addUser(@RequestBody AppUser user) {
        userService.createUser(user);
    }

    @PostMapping("/locations")
    public void addLocation(@AuthenticationPrincipal String nickname, @RequestBody Location location) {
        AppUser user = userRepository.findByNickname(nickname).orElseThrow(NoSuchElementException::new);
        userService.addLocation(location, user);
    }
}
