package com.bibro.rain_notification.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String longitude;
    private String latitude;
    @ManyToOne
    private AppUser user;

    public Location(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
