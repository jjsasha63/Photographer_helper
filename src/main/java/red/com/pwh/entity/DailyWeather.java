package red.com.pwh.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DailyWeather {

    private List<LocalDate> time;
    private List<Integer> weathercode;
    private List<Double> temperature_2m_max;
    private List<Double> temperature_2m_min;
    private List<LocalDateTime> sunrise;
    private List<LocalDateTime> sunset;
    private List<Double> precipitation_sum;
    private List<Double> precipitation_hours;
    private List<Double> windspeed_10m_max;

    public List<LocalDate> getTime() {
        return time;
    }

    public void setTime(List<LocalDate> time) {
        this.time = time;
    }

    public List<Integer> getWeathercode() {
        return weathercode;
    }

    public void setWeathercode(List<Integer> weathercode) {
        this.weathercode = weathercode;
    }

    public List<Double> getTemperature_2m_max() {
        return temperature_2m_max;
    }

    public void setTemperature_2m_max(List<Double> temperature_2m_max) {
        this.temperature_2m_max = temperature_2m_max;
    }

    public List<Double> getTemperature_2m_min() {
        return temperature_2m_min;
    }

    public void setTemperature_2m_min(List<Double> temperature_2m_min) {
        this.temperature_2m_min = temperature_2m_min;
    }

    public List<LocalDateTime> getSunrise() {
        return sunrise;
    }

    public void setSunrise(List<LocalDateTime> sunrise) {
        this.sunrise = sunrise;
    }

    public List<LocalDateTime> getSunset() {
        return sunset;
    }

    public void setSunset(List<LocalDateTime> sunset) {
        this.sunset = sunset;
    }

    public List<Double> getPrecipitation_sum() {
        return precipitation_sum;
    }

    public void setPrecipitation_sum(List<Double> precipitation_sum) {
        this.precipitation_sum = precipitation_sum;
    }

    public List<Double> getPrecipitation_hours() {
        return precipitation_hours;
    }

    public void setPrecipitation_hours(List<Double> precipitation_hours) {
        this.precipitation_hours = precipitation_hours;
    }

    public List<Double> getWindspeed_10m_max() {
        return windspeed_10m_max;
    }

    public void setWindspeed_10m_max(List<Double> windspeed_10m_max) {
        this.windspeed_10m_max = windspeed_10m_max;
    }

    @Override
    public String toString() {
        return "DailyWeather{" +
                "time=" + time +
                ", weathercode=" + weathercode +
                ", temperature_2m_max=" + temperature_2m_max +
                ", temperature_2m_min=" + temperature_2m_min +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", precipitation_sum=" + precipitation_sum +
                ", precipitation_hours=" + precipitation_hours +
                ", windspeed_10m_max=" + windspeed_10m_max +
                '}';
    }
}
