package com.bibro.rain_notification.repository;

import com.bibro.rain_notification.domain.user.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {

}
