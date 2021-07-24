package com.baml.matching.util;

import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Log4j2
public class MEDateUtils {

    private MEDateUtils(){
        throw new UnsupportedOperationException("Instantiation Restricted");
    }

    public static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static long getCurrentNanos(){
        ZonedDateTime zdt = LocalDateTime.now().atZone(ZoneId.of("GMT"));
        Instant instant = zdt.toInstant();
        return instant.getEpochSecond() * 100000000L + instant.getNano();
    }

    public static void pause(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Interrupted while sleep ", e);
            Thread.currentThread().interrupt();
        }
    }


}
