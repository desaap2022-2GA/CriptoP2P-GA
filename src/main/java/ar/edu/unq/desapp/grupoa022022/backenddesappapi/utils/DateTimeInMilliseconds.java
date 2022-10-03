package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import java.time.ZonedDateTime;

public class DateTimeInMilliseconds {

    private final long currentTimeInMilliseconds = ZonedDateTime.now().toInstant().toEpochMilli();

    private final long currentTimeMinusOneDayInMilliseconds = ZonedDateTime.now().minusDays(1).toInstant().toEpochMilli();

    private final long currentTimeMinus30MinutesInMilliseconds = ZonedDateTime.now().minusMinutes(30).toInstant().toEpochMilli();

    public long getCurrentTimeInMilliseconds() {
        return currentTimeInMilliseconds;
    }

    public long getCurrentTimeMinusOneDayInMilliseconds() {
        return currentTimeMinusOneDayInMilliseconds;
    }

    public long getCurrentTimeMinus30MinutesInMilliseconds() {
        return currentTimeMinus30MinutesInMilliseconds;
    }
}
