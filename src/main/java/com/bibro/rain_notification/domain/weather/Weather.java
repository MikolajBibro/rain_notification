package com.bibro.rain_notification.domain.weather;

import lombok.Data;

import java.util.Arrays;

public enum Weather {
    NO_RAIN1(-32, "no rain", false),
    NO_RAIN(0, "no rain", false),
    HARDLY_NOTICEABLE(5, "hardly noticeable", true),
    LIGHT_MIST(10, "light mist", true),
    MIST(15, "mist", true),
    VERY_LIGHT(20, " very light", true),
    LIGHT(25, "light rain", true),
    LIGHT_TO_MODERATE(30, "light to moderate", true),
    MODERATE_RAIN(35, "moderate rain", true),
    MODERATE_RAIN2(40, "moderate rain", true),
    MODERATE_TO_HEAVY(45, "moderate to heavy", true),
    HEAVY(50, "heavy", true);

    private int dBZ;
    private String description;
    private boolean isRainy;

    Weather(int dBZ, String description, boolean isRainy) {
        this.dBZ = dBZ;
        this.description = description;
        this.isRainy = isRainy;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRainy() {
        return isRainy;
    }

    public static Weather valueOf(int value) {
        return Arrays.stream(values())
                .filter(v -> v.dBZ == value - (value % 5))
                .findFirst()
                .get();

    }

}
