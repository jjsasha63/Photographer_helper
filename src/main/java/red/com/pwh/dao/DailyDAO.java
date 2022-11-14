package red.com.pwh.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import red.com.pwh.entity.DailyWeather;
import red.com.pwh.entity.Weather;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DailyDAO implements DailyDAOInterface {

    @Value("${weather.api}")
    private String apiLink;

    @Value("${weather.daily}")
    private String dailyLink;

    @Value("${weather.latitude}")
    private String latitudeLink;

    @Value("${weather.longitude}")
    private String longitudeLink;

    @Value("${weather.timezone}")
    private String timezoneLink;

    private DailyWeather dailyWeather;

    private final Weather weather = new Weather();


    private static DailyWeather get(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(url, DailyWeather.class);
    }

    @Override
    public void load_week(Double latitude, Double longitude, String timezone) throws IOException {
        URL url = new URL(apiLink + latitudeLink + latitude + longitudeLink + longitude + dailyLink + timezoneLink + timezone);
        dailyWeather = get(url);
    }

    @Override
    public List<LocalDate> date_list() {
        return dailyWeather.getTime();
    }


    private int day_index(LocalDate date){
        return dailyWeather.getTime().indexOf(date);
    }

    @Override
    public Double get_max_temp(LocalDate date){
        return dailyWeather.getTemperature_2m_max().get(day_index(date));
    }

    @Override
    public Double get_min_temp(LocalDate date){
        return dailyWeather.getTemperature_2m_min().get(day_index(date));
    }

    @Override
    public LocalTime get_sunet(LocalDate date){
        return dailyWeather.getSunset().get(day_index(date)).toLocalTime();
    }

    @Override
    public LocalTime get_sunrise(LocalDate date){
        return dailyWeather.getSunrise().get(day_index(date)).toLocalTime();
    }

    @Override
    public Double get_precipitation_sum(LocalDate date){
        return dailyWeather.getPrecipitation_sum().get(day_index(date));
    }

    @Override
    public Double get_precipitation_hours(LocalDate date){
        return dailyWeather.getPrecipitation_hours().get(day_index(date));
    }

    @Override
    public Double get_windspeed(LocalDate date){
        return dailyWeather.getWindspeed_10m_max().get(day_index(date));
    }


    @Override
    public String get_weather(LocalDate date) {
        return weather.get_weather(dailyWeather.getWeathercode().get(day_index(date)));
    }

    @Override
    public Integer get_weatherCode(LocalDate date) {
        return dailyWeather.getWeathercode().get(day_index(date));
    }

    @Override
    public DailyWeather get_week(){
        return dailyWeather;
    }

    @Override
    public List<LocalDate> get_timeList() {
        return dailyWeather.getTime();
    }

}
