package com.bibro.rain_notification.repository;

import com.bibro.rain_notification.domain.user.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<AppUser, Integer> {
    Optional<AppUser> findByNickname(String nickname);
    List<AppUser> findAll();
}
