package red.com.pwh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import red.com.pwh.dao.DailyDAOInterface;
import red.com.pwh.dao.HourlyDAOInterface;
import red.com.pwh.entity.DailyWeather;
import red.com.pwh.processing.ConditionsInterface;
import red.com.pwh.processing.LocationInterface;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PHService implements PHServiceInterface{

    private DailyDAOInterface dailyDAO;

    private HourlyDAOInterface hourlyDAO;

    private ConditionsInterface conditions;

    private LocationInterface location;

    private Double latitude;

    private Double longitude;

    private String timezone;

    private List<LocalDate> dates;

    private String loc;

    public PHService(){}
    @Autowired
    public PHService(LocationInterface location,DailyDAOInterface dailyDAO,HourlyDAOInterface hourlyDAO,ConditionsInterface conditions) throws IOException {
        this.location = location;
        this.dailyDAO = dailyDAO;
        this.hourlyDAO = hourlyDAO;
        this.conditions = conditions;
    }

    @PostConstruct
    public void def() throws IOException {
        latitude = 51.760932;
        longitude = 19.460718;
        timezone = "Europe/Warsaw";
        loc = "Lodz";
        load();
    }

    @Override
    public void set_location(String loc) throws IOException {
        Double[] loc_long = location.get_param(loc);
        latitude = loc_long[0];
        longitude = loc_long[1];
        timezone = location.get_timezone(loc);
        this.loc = loc;
        load();
    }

    @Override
    public void set_location(Double latitude, Double longitude) throws IOException {
        this.latitude = latitude;
        this.longitude = longitude;
        timezone = location.get_timezone(latitude,longitude);
        this.loc = location.get_address(latitude,longitude);
        load();
    }

    @Override
    public Double get_latitude() {
        return latitude;
    }

    @Override
    public Double get_longitude() {
        return longitude;
    }

    @Override
    public String get_timezone() {
        return timezone;
    }

    @Override
    public void load() throws IOException {
        dailyDAO.load_week(latitude,longitude,timezone);
        hourlyDAO.load_week(latitude,longitude,timezone);
        dates = dailyDAO.date_list();
    }

    @Override
    public void load(LocalDate date) throws IOException {
        hourlyDAO.load_day(latitude,longitude,timezone,date);
    }

    @Override
    public LocalTime[] get_sunset_sunrise(LocalDate date) {
        LocalTime[] arr = new LocalTime[2];
        arr[0] = dailyDAO.get_sunrise(date);
        arr[1] = dailyDAO.get_sunset(date);
        return arr;
    }

    @Override
    public List<LocalDate> get_dateList() {
        return dates;
    }

    @Override
    public Double[] get_options(LocalDate date) {
        Double[] options = new Double[5];
        options[0] = dailyDAO.get_max_temp(date);
        options[1] = dailyDAO.get_min_temp(date);
        options[2] = dailyDAO.get_windspeed(date);
        options[3] = dailyDAO.get_precipitation_sum(date);
        options[4] = dailyDAO.get_precipitation_hours(date);
        return options;
    }

    @Override
    public String get_weather(LocalDate date) {
        return dailyDAO.get_weather(date);
    }

    @Override
    public Integer get_weather_code(LocalDate date) {
        return dailyDAO.get_weatherCode(date);
    }

    @Override
    public List<Integer> get_weather_code() {
        return dailyDAO.get_weather_code();
    }

    @Override
    public Integer get_weather_code(LocalDateTime time) {
        return hourlyDAO.get_weatherCode(time);
    }

    @Override
    public Boolean is_night(LocalDateTime time) {
        return time.isAfter(conditions.get_evening_blue_hour_end(dailyDAO.get_sunset(time.toLocalDate())).atDate(time.toLocalDate()))
                || time.isBefore(conditions.get_morning_blue_hour_start(dailyDAO.get_sunrise(time.toLocalDate())).atDate(time.toLocalDate()));
    }

    @Override
    public List<String> get_weatherList(LocalDate date) throws IOException {
        List<String> weather = new ArrayList<>();
        load(date);
        for(LocalDateTime time: hourlyDAO.get_timeList()){
            weather.add(hourlyDAO.get_weather(time));
        }
        load();
        return weather;
    }

    @Override
    public DailyWeather get_week() {
        return dailyDAO.get_week();
    }


    @Override
    public List<Double> get_temp(LocalDate date) throws IOException {
        List<Double> temp = new ArrayList<>();
        load(date);
        for(LocalDateTime time: hourlyDAO.get_timeList()){
            temp.add(hourlyDAO.get_temperature(time));
        }
        load();
        return temp;
    }

    @Override
    public List<Double> get_wind(LocalDate date) throws IOException {
        List<Double> wind = new ArrayList<>();
        load(date);
        for(LocalDateTime time: hourlyDAO.get_timeList()){
            wind.add(hourlyDAO.get_windspeed(time));
        }
        load();
        return wind;
    }

    @Override
    public List<Double> get_prec(LocalDate date) throws IOException {
        List<Double> prec = new ArrayList<>();
        load(date);
        for(LocalDateTime time: hourlyDAO.get_timeList()){
            prec.add(hourlyDAO.get_precipitation(time));
        }
        load();
        return prec;
    }

    @Override
    public List<Integer> get_cloud(LocalDate date) throws IOException {
        List<Integer> cloud = new ArrayList<>();
        load(date);
        for(LocalDateTime time: hourlyDAO.get_timeList()){
            cloud.add(hourlyDAO.get_cloudcover(time));
        }
        load();
        return cloud;
    }


    @Override
    public LocalTime[] get_goldenHours(LocalDate date) {
        LocalTime[] times = new LocalTime[4];
        times[0] = dailyDAO.get_sunrise(date);
        times[1] = conditions.get_morning_golden_hour(times[0]);
        times[2] = conditions.get_evening_golden_hour(dailyDAO.get_sunset(date));
        times[3] = dailyDAO.get_sunset(date);
        return times;
    }

    @Override
    public LocalTime[] get_blueHours(LocalDate date) {
        LocalTime[] times = new LocalTime[4];
        times[0] = conditions.get_morning_blue_hour_start(dailyDAO.get_sunrise(date));
        times[1] = conditions.get_morning_blue_hour_end(dailyDAO.get_sunrise(date));
        times[2] = conditions.get_evening_blue_hour_start(dailyDAO.get_sunset(date));
        times[3] = conditions.get_evening_blue_hour_end(dailyDAO.get_sunset(date));
        return times;
    }

    @Override
    public LocalDate get_bestDay_optional(String ind) {
        return switch (ind){
            case "morning" -> conditions.best_date_morning();
            case "evening" -> conditions.best_date_evening();
            case "e_night" -> conditions.best_date_early_night();
            case "l_night" -> conditions.best_date_late_night();
            default -> conditions.best_date_overall();
        };
    }

    @Override
    public List<List<String>> get_hour_weather(LocalDate date) {
        List<List<String>> param = new ArrayList<>();
        for(LocalDateTime time:hourlyDAO.get_timeList_day(date)){
            List<String> params = new ArrayList<>();
            params.add(time.toLocalTime().toString());
            params.add(hourlyDAO.get_weather(time));
            params.add(hourlyDAO.get_temperature(time).toString());
            params.add(hourlyDAO.get_cloudcover(time).toString());
            params.add(hourlyDAO.get_precipitation(time).toString());
            params.add(hourlyDAO.get_windspeed(time).toString());
            param.add(params);
        }
        return param;
    }

    @Override
    public List<List<String>> get_day_weather() {
        List<List<String>> params = new ArrayList<>();
        for(int i=0;i<7;i++) {
            LocalDate date = dailyDAO.date_list().get(i);
            List<String> param = new ArrayList<>();
            param.add(date.getDayOfWeek().toString());
            param.add(dailyDAO.get_weather(date));
            param.add(dailyDAO.get_max_temp(date).toString());
            param.add(dailyDAO.get_min_temp(date).toString());
            param.add(dailyDAO.get_precipitation_sum(date).toString());
            param.add(dailyDAO.get_precipitation_hours(date).toString());
            param.add(dailyDAO.get_windspeed(date).toString());
            param.add(dailyDAO.get_sunrise(date).toString());
            param.add(dailyDAO.get_sunset(date).toString());
            params.add(param);
        }
        return params;
    }

    @Override
    public List<String> get_bestDay_list() {
        return new ArrayList<>(){{
           add(conditions.best_date_overall().getDayOfWeek().toString());
           add(conditions.best_date_morning().getDayOfWeek().toString());
           add(conditions.best_date_evening().getDayOfWeek().toString());
           add(conditions.best_date_early_night().getDayOfWeek().toString());
           add(conditions.best_date_late_night().getDayOfWeek().toString());
        }};
    }


    @Override
    public String get_address() throws IOException {
        return location.get_address(loc);
    }

    @Override
    public LocalDate reverse_date(String day) {
        for(LocalDate date:dailyDAO.date_list()){
            if(date.getDayOfWeek().toString().equals(day)) return date;
        }
        return LocalDate.now();
    }


}
