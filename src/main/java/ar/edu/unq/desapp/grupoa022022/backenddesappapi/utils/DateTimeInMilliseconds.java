package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

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

    public String getCurrentTimeInMillisecondsDateFormat() {
        return convertLongToDate(currentTimeInMilliseconds);
    }

    public String getCurrentTimeMinusOneDayInMillisecondsDateFormat() {
        return convertLongToDate(currentTimeMinusOneDayInMilliseconds);
    }

    public String getCurrentTimeMinus30MinutesInMillisecondsDateFormat() {
        return convertLongToDate(currentTimeMinus30MinutesInMilliseconds);
    }

    public String convertLongToDate (long timeInLong) {
        Date date = new Date();
        date.setTime(timeInLong);
       return new SimpleDateFormat().format(date);
    }
}
