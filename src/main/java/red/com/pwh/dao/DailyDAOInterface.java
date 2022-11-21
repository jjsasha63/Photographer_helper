package red.com.pwh.dao;

import red.com.pwh.entity.DailyWeather;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DailyDAOInterface {

    void load_week(Double latitude, Double longitude, String timezone) throws IOException;

    List<LocalDate> date_list();

    Double get_max_temp(LocalDate date);

    Double get_min_temp(LocalDate date);

    LocalTime get_sunset(LocalDate date);

    LocalTime get_sunrise(LocalDate date);

    Double get_precipitation_sum(LocalDate date);

    Double get_precipitation_hours(LocalDate date);

    Double get_windspeed(LocalDate date);

    String get_weather(LocalDate date);

    Integer get_weatherCode(LocalDate date);

    DailyWeather get_week();

    List<LocalDate> get_timeList();

}
