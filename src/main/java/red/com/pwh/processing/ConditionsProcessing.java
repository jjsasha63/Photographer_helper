package red.com.pwh.processing;

import red.com.pwh.dao.HourlyDAOInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConditionsProcessing implements ConditionsInterface {

    final Double TEMP_PORTION = 0.2;
    final Double PREC_PORTION = 0.2;
    final Double WEATHER_PORTION = 0.2;
    final Double CLOUD_PORTION = 0.2;
    final Double WIND_PORTION = 0.2;



    @Override
    public LocalTime get_morning_golden_hour(LocalTime sunrise) {
        return sunrise.plusHours(1);
    }

    @Override
    public LocalTime get_evening_golden_hour(LocalTime sunset) {
        return sunset.minusHours(1);
    }

    @Override
    public LocalTime get_evening_blue_hour_start(LocalTime sunset) {
        return sunset.plusHours(10);
    }

    @Override
    public LocalTime get_morning_blue_hour_start(LocalTime sunrise) {
        return sunrise.minusMinutes(30);
    }

    @Override
    public LocalTime get_evening_blue_hour_end(LocalTime sunset) {
        return sunset.plusHours(30);
    }

    @Override
    public LocalTime get_morning_blue_hour_end(LocalTime sunrise) {
        return sunrise.minusMinutes(10);
    }


    @Override
    public LocalDate best_date_overall(HourlyDAOInterface hourlyDAOInterface) {
        return best_day(hourlyDAOInterface,0,24);
    }

    @Override
    public LocalDate best_date_morning(HourlyDAOInterface hourlyDAOInterface, LocalTime sunrise) {
        return best_day(hourlyDAOInterface,sunrise.getHour()-1,12-(sunrise.getHour()-1));
    }
    @Override
    public LocalDate best_date_evening(HourlyDAOInterface hourlyDAOInterface, LocalTime sunset) {
        return best_day(hourlyDAOInterface,sunset.getHour()-3,4);
    }

    @Override
    public LocalDate best_date_early_night(HourlyDAOInterface hourlyDAOInterface, LocalTime sunset) {
        return best_day(hourlyDAOInterface,sunset.getHour()+1,24-(sunset.getHour()+1));
    }

    @Override
    public LocalDate best_date_late_night(HourlyDAOInterface hourlyDAOInterface, LocalTime sunrise) {
        return best_day(hourlyDAOInterface,0,sunrise.getHour()-1);
    }

    private Double temp_score(Double temp){
        if(temp<=25) return (25-temp)*2;
        else if(temp<=32)return temp-25;
        else if(temp<=45) return (temp-25)*5;
        else return 100.0;

    }

    private LocalDate best_day(HourlyDAOInterface hourlyDAOInterface,int start,int duration){
        LocalDateTime time = hourlyDAOInterface.get_timeList().get(0).plusHours(start);
        List<Double> week_scores = new ArrayList<>();
        List<LocalDate> dates = new ArrayList<>();
        for(int j = 0;j<7;j++) {
            Double avg_tmp = 0.0,avg_prec = 0.0,avg_weather = 0.0,avg_cloud = 0.0,avg_wind = 0.0;
            for (int i = 0; i <= duration; i++) {
                avg_tmp += hourlyDAOInterface.get_temperature(time.plusHours(i));
                avg_prec += hourlyDAOInterface.get_precipitation(time.plusHours(i));
                avg_weather += hourlyDAOInterface.get_weatherCode(time.plusHours(i));
                avg_cloud += hourlyDAOInterface.get_cloudcover(time.plusHours(i));
                avg_wind += hourlyDAOInterface.get_windspeed(time.plusHours(i));
            }
            week_scores.add(final_score(temp_score(avg_tmp/7),prec_score(avg_prec/7),avg_weather/7,avg_cloud/7,wind_score(avg_wind/7)));
            dates.add(time.toLocalDate());
            time = time.plusHours(24);
        }
        return dates.get(week_scores.indexOf(Collections.min(week_scores)));
    }

    private Double prec_score(Double prec){
        prec*=10;
        if(prec<=100) return prec;
        else return 100.0;
    }


    private Double wind_score(Double wind){
        wind*=3;
        if(wind<=100) return wind;
        else return 100.0;
    }

    private Double final_score(Double temp,Double prec,Double weather, Double cloud, Double wind){
        return temp*TEMP_PORTION+prec*PREC_PORTION+weather*WEATHER_PORTION+cloud*CLOUD_PORTION+wind*WIND_PORTION;
    }

}
