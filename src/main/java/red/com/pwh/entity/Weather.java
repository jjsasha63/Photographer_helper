package red.com.pwh.entity;

import java.util.HashMap;

public class Weather {

    private HashMap<Integer,String> weather_type = new HashMap<>(){{
        put(0,"Clear sky");
        put(1,"Mainly clear");
        put(2,"Partly cloudy");
        put(3,"Overcast");
        put(45,"Fog");
        put(48,"Depositing rime fog");
        put(51,"Light drizzle");
        put(53,"Moderate drizzle");
        put(55,"Densely intensive drizzle");
        put(56,"Light freezing drizzle");
        put(57,"Densely intensive freezing drizzle");
        put(61,"Slight rain");
        put(63,"Moderate rain");
        put(65,"Heavily intensive rain");
        put(66,"Light freezing rain");
        put(67,"Heavily intensive freezing rain");
        put(71,"Slight snow fall");
        put(73,"Moderate snow fall");
        put(75,"Heavily intensive snow fall");
        put(77,"Snow grains");
        put(80,"Slight rain showers");
        put(81,"Moderate rain showers");
        put(82,"Violent rain showers");
        put(85,"Slight snow showers");
        put(86,"Heavy snow showers");
        put(95,"Thunderstorm");
        put(96,"Thunderstorm with slight hail");
        put(99,"Thunderstorm with heavy hail");
    }};

   public String get_weather(int type){
        return weather_type.get(type);
    }

}
