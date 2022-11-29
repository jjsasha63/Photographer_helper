package red.com.pwh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import red.com.pwh.entity.DailyWeather;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface PHServiceInterface {

    void set_location(String loc) throws IOException;


    void set_location(Double latitude, Double longitude) throws IOException;

    Double get_latitude();

    Double get_longitude();

    String get_timezone();

    void load() throws IOException;

    void load(LocalDate date) throws IOException;

    LocalTime[] get_sunset_sunrise(LocalDate date);

    List<LocalDate> get_dateList();

    Double[] get_options(LocalDate date);

    String get_weather(LocalDate date);

    Integer get_weather_code(LocalDate date);

    Integer get_weather_code(LocalDateTime time);

    Boolean is_night(LocalDateTime time);

    List<String> get_weatherList(LocalDate timedate) throws IOException;

    DailyWeather get_week();


    List<Double> get_temp(LocalDate date) throws IOException;

    List<Double> get_wind(LocalDate date) throws IOException;

    List<Double> get_prec(LocalDate date) throws IOException;

    List<Integer> get_cloud(LocalDate date) throws IOException;


    LocalTime[] get_goldenHours(LocalDate date);

    LocalTime[] get_blueHours(LocalDate date);

    LocalDate get_bestDay_optional(String ind);

    List<List<String>> get_day_weather();

    List<List<String>> get_hour_weather(LocalDate date);

    List<String> get_bestDay_list();
    String get_address() throws IOException;

    LocalDate reverse_date(String day);


}
