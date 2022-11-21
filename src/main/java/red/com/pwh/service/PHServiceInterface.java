package red.com.pwh.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface PHServiceInterface {

    void set_location(String loc) throws JsonProcessingException;

    void set_location(Double latitude, Double longitude) throws JsonProcessingException;

    Double get_latitude();

    Double get_longitude();

    String get_timezone();

    void load() throws IOException;

    void load(LocalDate date) throws IOException;

    LocalTime[] get_sunset_sunrise(LocalDate date);

    List<LocalDate> get_dateList();

    Double[] get_options(LocalDate date);

    String get_weather(LocalDate date);

    List<String> get_weatherList(LocalDate timedate) throws IOException;

    List<Double> get_temp(LocalDate date) throws IOException;

    List<Double> get_wind(LocalDate date) throws IOException;

    List<Double> get_prec(LocalDate date) throws IOException;

    List<Integer> get_cloud(LocalDate date) throws IOException;

    LocalDate get_bestDay();

    LocalTime[] get_goldenHours(LocalDate date);

    LocalTime[] get_blueHours(LocalDate date);

    LocalDate get_bestDay_optional(String ind, LocalDate date);

    String get_address() throws JsonProcessingException;



}
