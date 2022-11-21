package red.com.pwh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import red.com.pwh.dao.DailyDAOInterface;
import red.com.pwh.dao.HourlyDAOInterface;
import red.com.pwh.processing.ConditionsInterface;
import red.com.pwh.processing.LocationInterface;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PHService implements PHServiceInterface{

    private DailyDAOInterface dailyDAO;

    private HourlyDAOInterface hourlyDAO;

    private ConditionsInterface conditions;

    private LocationInterface location;

    private Double latitude;

    private Double longitude;

    private String timezone;

    private List<LocalDate> dates;

    @Autowired
    public PHService(LocationInterface location,DailyDAOInterface dailyDAO,HourlyDAOInterface hourlyDAO,ConditionsInterface conditions) throws IOException {
        this.location = location;
        this.dailyDAO = dailyDAO;
        this.hourlyDAO = hourlyDAO;
        this.conditions = conditions;
        load();
    }

    @Override
    public void set_location(String loc) throws JsonProcessingException {
        Double[] loc_long = location.get_param(loc);
        latitude = loc_long[0];
        longitude = loc_long[1];
        timezone = location.get_timezone(loc);
    }

    @Override
    public void set_location(Double latitude, Double longitude) throws JsonProcessingException {
        this.latitude = latitude;
        this.longitude = longitude;
        timezone = location.get_timezone(latitude,longitude);
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
    public LocalDate get_bestDay() {
        return conditions.best_date_overall(hourlyDAO);
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
    public LocalDate get_bestDay_optional(String ind,LocalDate date) {
        LocalDate c_date = date;
        switch (ind){
            case "morning" -> c_date = conditions.best_date_morning(hourlyDAO,dailyDAO.get_sunrise(date));
            case "evening" -> c_date = conditions.best_date_evening(hourlyDAO,dailyDAO.get_sunset(date));
            case "e_night" -> c_date = conditions.best_date_early_night(hourlyDAO,dailyDAO.get_sunset(date));
            case "l_night" -> c_date = conditions.best_date_late_night(hourlyDAO,dailyDAO.get_sunrise(date));
        }
        return c_date;
    }

    @Override
    public String get_address() throws JsonProcessingException {
        return location.get_address(latitude,longitude);
    }
}
