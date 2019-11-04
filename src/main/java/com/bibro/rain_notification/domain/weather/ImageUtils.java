package com.bibro.rain_notification.domain.weather;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImageUtils {

    private static List<Integer> getPixelValues(BufferedImage image) {
        List<Integer> colors = new ArrayList<>();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color pixel = new Color(image.getRGB(i, j));
                colors.add(pixel.getRed());
            }
        }
        return colors;
    }

    static int getMostFrequentPixelValue(BufferedImage image) {
        return getPixelValues(image).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }
}