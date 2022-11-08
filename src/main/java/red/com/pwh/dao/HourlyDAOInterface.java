package red.com.pwh.dao;


import red.com.pwh.entity.HourlyWeather;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface HourlyDAOInterface {

    void load_day(Double latitude, Double longitude, String timezone, LocalDate date) throws IOException;

    List<LocalDateTime> get_timeList();

    HourlyWeather get_weather();

    Double get_temperature(LocalDateTime time);

    Double get_precipitation(LocalDateTime time);

    String get_weather(LocalDateTime time);

    Integer get_weatherCode(LocalDateTime time);

    Integer get_cloudcover(LocalDateTime time);

    Double get_windspeed(LocalDateTime time);
}
