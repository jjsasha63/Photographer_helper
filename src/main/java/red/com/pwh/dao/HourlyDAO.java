package red.com.pwh.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import red.com.pwh.entity.HourlyWeather;
import red.com.pwh.entity.Weather;

import java.io.IOException;
import java.net.URL;
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

    private HourlyWeather hourlyWeather;

    final private Weather weather = new Weather();

    private static HourlyWeather get(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(url, HourlyWeather.class);
    }

    private int hour_index(LocalDateTime date){
        return hourlyWeather.getTime().indexOf(date);
    }

    @Override
    public void load_day(Double latitude, Double longitude, String timezone, LocalDate date) throws IOException {
        URL url = new URL(apiLink + latitudeLink + latitude + longitudeLink + longitude + hourlyLink + timezoneLink + timezone + startLink + date + endLink + date);
        hourlyWeather = get(url);
    }

    @Override
    public void load_week(Double latitude, Double longitude, String timezone) throws IOException {
        URL url = new URL(apiLink + latitudeLink + latitude + longitudeLink + longitude + hourlyLink + timezoneLink + timezone);
        hourlyWeather = get(url);
    }

    @Override
    public List<LocalDateTime> get_timeList() {
        return hourlyWeather.getTime();
    }

    @Override
    public HourlyWeather get_weather() {
        return hourlyWeather;
    }

    @Override
    public Double get_temperature(LocalDateTime time) {
        return hourlyWeather.getTemperature_2m().get(hour_index(time));
    }

    @Override
    public Double get_precipitation(LocalDateTime time) {
        return hourlyWeather.getPrecipitation().get(hour_index(time));
    }

    @Override
    public String get_weather(LocalDateTime time) {
        return weather.get_weather(hourlyWeather.getWeathercode().get(hour_index(time)));
    }

    @Override
    public Integer get_weatherCode(LocalDateTime time) {
        return hourlyWeather.getWeathercode().get(hour_index(time));
    }

    @Override
    public Integer get_cloudcover(LocalDateTime time) {
        return hourlyWeather.getCloudcover().get(hour_index(time));
    }

    @Override
    public Double get_windspeed(LocalDateTime time) {
        return hourlyWeather.getWindspeed_10m().get(hour_index(time));
    }
}
