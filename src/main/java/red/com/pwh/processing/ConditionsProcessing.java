package red.com.pwh.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import red.com.pwh.dao.DailyDAOInterface;
import red.com.pwh.dao.HourlyDAOInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ConditionsProcessing implements ConditionsInterface {

    final Double TEMP_PORTION = 0.2;
    final Double PREC_PORTION = 0.2;
    final Double WEATHER_PORTION = 0.2;
    final Double CLOUD_PORTION = 0.2;
    final Double WIND_PORTION = 0.2;

    DailyDAOInterface daoInterface;

    HourlyDAOInterface hourlyDAOInterface;

    @Autowired
    public void setDaoInterface(DailyDAOInterface daoInterface) {
        this.daoInterface = daoInterface;
    }

    @Autowired
    public void setHourlyDAOInterface(HourlyDAOInterface hourlyDAOInterface) {
        this.hourlyDAOInterface = hourlyDAOInterface;
    }

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
    public LocalDate best_date_overall() {
        return best_day("");
    }

    @Override
    public LocalDate best_date_morning() {
        return best_day("morning");
    }
    @Override
    public LocalDate best_date_evening() {
        return best_day("evening");
    }

    @Override
    public LocalDate best_date_early_night() {
        return best_day("e_night");
    }

    @Override
    public LocalDate best_date_late_night() {
        return best_day("l_night");
    }

    private Double temp_score(Double temp){
        if(temp<=25) return (25-temp)*2;
        else if(temp<=32)return temp-25;
        else if(temp<=45) return (temp-25)*5;
        else return 100.0;

    }

    private int[] get_interval(String arg,LocalDate date){
        int[] res = new int[2];
        return switch (arg) {
            case "morning" ->
                    new int[]{daoInterface.get_sunrise(date).getHour() - 1, 12 - (daoInterface.get_sunrise(date).getHour() - 1)};
            case "evening" -> new int[]{daoInterface.get_sunset(date).getHour() - 3, 4};
            case "e_night" ->
                    new int[]{daoInterface.get_sunset(date).getHour() + 1, 24 - (daoInterface.get_sunset(date).getHour() + 1)};
            case "l_night" -> new int[]{0, daoInterface.get_sunrise(date).getHour() - 1};
            default -> new int[]{0, 24};
        };
    }

    private LocalDate best_day(String arg){
        List<Double> week_scores = new ArrayList<>();
        for(LocalDate dt:daoInterface.get_timeList()){
            int[] hours = get_interval(arg,dt);
            hours[0] = Math.max(hours[0], 0);
            hours[1] = Math.max(hours[1], 1);
            hours[0] = Math.min(hours[0], 23);
            hours[1] = Math.min(hours[1], 23);
            LocalDateTime time = dt.atTime(LocalTime.of(hours[0],0));
            Double avg_tmp = 0.0,avg_prec = 0.0,avg_weather = 0.0,avg_cloud = 0.0,avg_wind = 0.0;
                for (int i = 0; i < hours[1]; i++) {
                    avg_tmp += hourlyDAOInterface.get_temperature(time.plusHours(i));
                    avg_prec += hourlyDAOInterface.get_precipitation(time.plusHours(i));
                    avg_weather += hourlyDAOInterface.get_weatherCode(time.plusHours(i));
                    avg_cloud += hourlyDAOInterface.get_cloudcover(time.plusHours(i));
                    avg_wind += hourlyDAOInterface.get_windspeed(time.plusHours(i));
            }
            week_scores.add(final_score(temp_score(avg_tmp/7),prec_score(avg_prec/7),avg_weather/7,avg_cloud/7,wind_score(avg_wind/7)));;
        }
        return daoInterface.get_timeList().get(week_scores.indexOf(Collections.min(week_scores)));
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
