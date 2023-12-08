package id.kevinchristian.snap.util;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {
    private static final String timeZone = "Asia/Jakarta";

    TimeUtil() {
        throw new IllegalStateException();
    }

    public static Date localDateTimeToDate(LocalDateTime localdateTime) {
        return Date.from(localdateTime.atZone(ZoneId.of(timeZone)).toInstant());
    }
}
