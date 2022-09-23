package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils;

import java.time.ZonedDateTime;

public class DateTimeInMilliseconds {

    public long currentTimeInMilliseconds = ZonedDateTime.now().toInstant().toEpochMilli();

    public long currentTimeMinusOneDayInMilliseconds = ZonedDateTime.now().minusDays(1).toInstant().toEpochMilli();

    public long currentTimeMinus30MinutesInMilliseconds = ZonedDateTime.now().minusMinutes(30).toInstant().toEpochMilli();

}
