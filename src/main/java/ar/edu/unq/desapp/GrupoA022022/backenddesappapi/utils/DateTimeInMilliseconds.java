package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils;

import java.time.ZonedDateTime;

public class DateTimeInMilliseconds {

    public long currentTimeInMilliseconds = ZonedDateTime.now().toInstant().toEpochMilli();
}
