package red.com.pwh.entity;

import java.time.LocalDateTime;
import java.util.List;

public class HourlyWeather {

    private List<LocalDateTime> time;
    private List<Double> temperature_2m;
    private List<Double> precipitation;
    private List<Integer> weathercode;
    private List<Integer> cloudcover;
    private List<Double> windspeed_10m;

    public List<LocalDateTime> getTime() {
        return time;
    }

    public void setTime(List<LocalDateTime> time) {
        this.time = time;
    }

    public List<Double> getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(List<Double> temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public List<Double> getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(List<Double> precipitation) {
        this.precipitation = precipitation;
    }

    public List<Integer> getWeathercode() {
        return weathercode;
    }

    public void setWeathercode(List<Integer> weathercode) {
        this.weathercode = weathercode;
    }

    public List<Integer> getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(List<Integer> cloudcover) {
        this.cloudcover = cloudcover;
    }

    public List<Double> getWindspeed_10m() {
        return windspeed_10m;
    }

    public void setWindspeed_10m(List<Double> windspeed_10m) {
        this.windspeed_10m = windspeed_10m;
    }

    @Override
    public String toString() {
        return "HourlyWeather{" +
                "windspeed_10m_max=" + time +
                ", temperature_2m=" + temperature_2m +
                ", precipitation=" + precipitation +
                ", weathercode=" + weathercode +
                ", cloudcover=" + cloudcover +
                ", windspeed_10m=" + windspeed_10m +
                '}';
    }
}
