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
    }};

    public Weather(){

    }

}
