package red.com.pwh.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import red.com.pwh.entity.DailyWeather;
import red.com.pwh.entity.Weather;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
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


    private void get(URL url) throws IOException {
        dailyWeather = new DailyWeather();
        ObjectMapper mapper = new ObjectMapper();
        List<LocalDate> time =new ArrayList<>();
        List<Integer> weathercode=new ArrayList<>();
        List<Double> temperature_2m_max=new ArrayList<>();
        List<Double> temperature_2m_min=new ArrayList<>();
        List<LocalDateTime> sunrise=new ArrayList<>();
        List<LocalDateTime> sunset=new ArrayList<>();
        List<Double> precipitation_sum=new ArrayList<>();
        List<Double> precipitation_hours=new ArrayList<>();
        List<Double> windspeed_10m_max=new ArrayList<>();
        JsonNode jsonNode = mapper.readTree(url);

            for(JsonNode j:jsonNode.get("daily").get("time")) time.add(LocalDate.parse(j.asText()));
            for(JsonNode j:jsonNode.get("daily").get("sunrise")) sunrise.add(LocalDateTime.parse(j.asText()));
            for(JsonNode j:jsonNode.get("daily").get("sunset")) sunset.add(LocalDateTime.parse(j.asText()));
            for(JsonNode j:jsonNode.get("daily").get("weathercode")) weathercode.add(j.asInt());
            for(JsonNode j:jsonNode.get("daily").get("temperature_2m_max")) temperature_2m_max.add(j.asDouble());
            for(JsonNode j:jsonNode.get("daily").get("temperature_2m_min")) temperature_2m_min.add(j.asDouble());
            for(JsonNode j:jsonNode.get("daily").get("precipitation_sum")) precipitation_sum.add(j.asDouble());
            for(JsonNode j:jsonNode.get("daily").get("precipitation_hours")) precipitation_hours.add(j.asDouble());
            for(JsonNode j:jsonNode.get("daily").get("windspeed_10m_max")) windspeed_10m_max.add(j.asDouble());

            dailyWeather.setTime(time);
            dailyWeather.setSunrise(sunrise);
            dailyWeather.setSunset(sunset);
            dailyWeather.setWeathercode(weathercode);
            dailyWeather.setTemperature_2m_max(temperature_2m_max);
            dailyWeather.setTemperature_2m_min(temperature_2m_min);
            dailyWeather.setPrecipitation_sum(precipitation_sum);
            dailyWeather.setPrecipitation_hours(precipitation_hours);
            dailyWeather.setWindspeed_10m_max(windspeed_10m_max);

    }

    @Override
    public void load_week(Double latitude, Double longitude, String timezone) throws IOException {
        get(new URL(apiLink + latitudeLink + latitude + longitudeLink + longitude + dailyLink + timezoneLink + timezone));
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
    public LocalTime get_sunset(LocalDate date){
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
