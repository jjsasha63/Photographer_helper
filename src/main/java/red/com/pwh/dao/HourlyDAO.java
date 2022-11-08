package red.com.pwh.dao;

import org.springframework.beans.factory.annotation.Value;
import red.com.pwh.entity.HourlyWeather;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HourlyDAO implements HourlyDAOInterface {

    @Value("${weather.api}")
    private String apiLink;

    @Value("${weather.hourly}")
    private String hourlyLink;

    @Value("${weather.latitude}")
    private String latitudeLink;

    @Value("${weather.longitude}")
    private String longitudeLink;

    @Value("${weather.timezone}")
    private String timezoneLink;

    @Value("${weather.start}")
    private String startLink;

    @Value("${weather.end}")
    private String endLink;

    @Override
    public void load_day(Double latitude, Double longitude, String timezone, LocalDate date) throws IOException {

    }

    @Override
    public List<LocalDateTime> get_timeList() {
        return null;
    }

    @Override
    public HourlyWeather get_weather() {
        return null;
    }

    @Override
    public Double get_temperature(LocalDateTime time) {
        return null;
    }

    @Override
    public Double get_precipitation(LocalDateTime time) {
        return null;
    }

    @Override
    public String get_weather(LocalDateTime time) {
        return null;
    }

    @Override
    public Integer get_cloudcover(LocalDateTime time) {
        return null;
    }

    @Override
    public Double get_windspeed(LocalDateTime time) {
        return null;
    }
}
