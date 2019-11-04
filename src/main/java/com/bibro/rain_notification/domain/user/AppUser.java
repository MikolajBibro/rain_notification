package com.bibro.rain_notification.domain.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Data
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickname;
    private String password;
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Location> locations = new ArrayList<>();
    private LocalDateTime lastRainyWeather;

    public AppUser(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public AppUser(String nickname, String password, String email) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public void addLocation(Location location) {
        location.setUser(this);
        locations.add(location);
    }
}
