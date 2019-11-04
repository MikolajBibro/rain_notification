package com.bibro.rain_notification.domain.weather;

import com.bibro.rain_notification.domain.user.Location;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Scanner;

@Component
public class WeatherApi {

    private final Cache<String, String> timestampCache = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(15)).build();

    public Weather getWeather(Location location) {
        try {
            return tryGetWeather(location);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Weather tryGetWeather(Location location) throws IOException {
        String url = "https://tilecache.rainviewer.com/v2/radar/" + getTimestamp() + "/256/17/" + location.getLatitude() + "/" + location.getLongitude() + "/0/0_0.png";
        int dBZ = ImageUtils.getMostFrequentPixelValue(ImageIO.read(new URL(url))) - 32;
        return Weather.valueOf(dBZ);
    }

    private String getTimestamp() {
        return timestampCache.get("timestamp", it -> tryGetTimestampFromApi());
    }

    private String tryGetTimestampFromApi() {
        try {
            return getTimestampFromApi();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTimestampFromApi() throws IOException {
        String url = "https://tilecache.rainviewer.com/api/maps.json";
        String jsonTimes = new Scanner(new URL(url).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
        String[] times = new Gson().fromJson(jsonTimes, String[].class);
        return times[times.length - 1];
    }
}
