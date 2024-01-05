package com.artformgames.plugin.tempflight.utils;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {

    // Duration like [?d][?h][?m][?s]
    private static final Pattern DURATION_PATTERN = Pattern.compile("^(\\d+[dhms]?)+$");


    public static Duration parseDuration(String timeString) {
        Matcher matcher = Pattern.compile("(\\d+)([dhms]?)").matcher(timeString);
        Duration duration = Duration.ZERO;
        while (matcher.find()) {
            long value = Long.parseLong(matcher.group(1));
            duration = switch (matcher.group(2)) {
                case "d" -> duration.plusDays(value);
                case "h" -> duration.plusHours(value);
                case "m" -> duration.plusMinutes(value);
                default -> duration.plusSeconds(value);
            };
        }
        return duration;
    }

}
