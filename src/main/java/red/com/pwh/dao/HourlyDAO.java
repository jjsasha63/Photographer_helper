package red.com.pwh.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import red.com.pwh.entity.HourlyWeather;
import red.com.pwh.entity.Weather;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
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

    private void get(URL url) throws IOException {
        hourlyWeather = new HourlyWeather();
        ObjectMapper mapper = new ObjectMapper();
        List<LocalDateTime> time = new ArrayList<>();
        List<Double> temperature_2m = new ArrayList<>();
        List<Double> precipitation = new ArrayList<>();
        List<Integer> weathercode = new ArrayList<>();
        List<Integer> cloudcover = new ArrayList<>();
        List<Double> windspeed_10m = new ArrayList<>();
        JsonNode jsonNode = mapper.readTree(url);

        for(JsonNode j:jsonNode.get("hourly").get("time")) time.add(LocalDateTime.parse(j.asText()));
        for(JsonNode j:jsonNode.get("hourly").get("temperature_2m")) temperature_2m.add(j.asDouble());
        for(JsonNode j:jsonNode.get("hourly").get("precipitation")) precipitation.add(j.asDouble());
        for(JsonNode j:jsonNode.get("hourly").get("weathercode")) weathercode.add(j.asInt());
        for(JsonNode j:jsonNode.get("hourly").get("cloudcover")) cloudcover.add(j.asInt());
        for(JsonNode j:jsonNode.get("hourly").get("windspeed_10m")) windspeed_10m.add(j.asDouble());

        hourlyWeather.setTime(time);
        hourlyWeather.setCloudcover(cloudcover);
        hourlyWeather.setPrecipitation(precipitation);
        hourlyWeather.setTemperature_2m(temperature_2m);
        hourlyWeather.setWindspeed_10m(windspeed_10m);
        hourlyWeather.setWeathercode(weathercode);
    }

    private int hour_index(LocalDateTime date){
        return hourlyWeather.getTime().indexOf(date);
    }

    @Override
    public void load_day(Double latitude, Double longitude, String timezone, LocalDate date) throws IOException {
        get(new URL(apiLink + latitudeLink + latitude + longitudeLink + longitude + hourlyLink + timezoneLink + timezone + startLink + date + endLink + date));
    }

    @Override
    public void load_week(Double latitude, Double longitude, String timezone) throws IOException {
        get(new URL(apiLink + latitudeLink + latitude + longitudeLink + longitude + hourlyLink + timezoneLink + timezone));
    }

    @Override
    public List<LocalDateTime> get_timeList() {
        return hourlyWeather.getTime();
    }

    @Override
    public List<LocalDateTime> get_timeList_day(LocalDate date) {
        List<LocalDateTime> times = new ArrayList<>();
        List<LocalDateTime> time = hourlyWeather.getTime();
        for(int i=0;i<7;i++){
            LocalDate date1 = time.get(i*24).toLocalDate();
            if(time.get(i*24).toLocalDate().equals(date)){
                for(int j=i;j<i+24;j++) times.add(time.get(j));
            }
        }
        return times;
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
