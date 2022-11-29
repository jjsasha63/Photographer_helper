package red.com.pwh.processing;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import red.com.pwh.exeption.LocationNotFoundException;

import java.io.IOException;
import java.net.URL;

@Component
public class Location implements LocationInterface {

    @Value("${positionstack.api.key}")
    private String key;

    @Value("${positionstack.api.forward}")
    private String apiF;

    @Value("${positionstack.api.reverse}")
    private String apiR;


    @Value("${positionstack.api.query}")
    private String query;

    @Value("${positionstack.api.limit}")
    private String limit;

    @Value("${positionstack.api.timezone}")
    private String timezone;

    @Value("${positionstack.api.fields}")
    private String fields;

    @Value("${positionstack.api.fields.ll}")
    private String lat_long;


    @Override
    public String get_address(String input) throws IOException {
        URL url = new URL(apiF+key+query+input+limit+fields);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        return getAddressString(jsonNode);
    }

    private String getAddressString(JsonNode jsonNode) {
        String country = "",region = "",loc="";
        for(JsonNode n: jsonNode.get("data")) {
           country = n.get("country").asText();
           region = n.get("region").asText();
           loc = n.get("locality").asText();
        }
        return country + " " + region + " " + loc;
    }


    @Override
    public String get_address(Double latitude, Double longitude) throws IOException {
        URL url = new URL(apiR+key+query+latitude+","+longitude+limit+fields);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        return getAddressString(jsonNode);
    }

    @Override
    public Double[] get_param(String input) throws IOException {
        URL url = new URL(apiF+key+query+input+lat_long);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        Double latitude = null,longitude = null;
        for(JsonNode n: jsonNode.get("data")) {
            if(n.get("latitude")!=null&&n.get("longitude")!=null) {
                latitude = n.get("latitude").asDouble();
                longitude = n.get("longitude").asDouble();
            }
        }
        if(latitude == null||longitude == null) throw new LocationNotFoundException("The location wasn't found");
        return new Double[]{latitude,longitude};
    }


    @Override
    public String get_timezone(String input) throws IOException {
        URL url = new URL(apiF+key+query+input+timezone+limit);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        String name = null;
        for(JsonNode n: jsonNode.get("data")) name = n.get("timezone_module").get("name").asText();
        return name;
    }

    @Override
    public String get_timezone(Double latitude, Double longitude) throws IOException {
        URL url = new URL(apiR+key+query+latitude+","+longitude+timezone+limit);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        String name = null;
        for(JsonNode n: jsonNode.get("data")) name = n.get("timezone_module").get("name").asText();
        return name;
    }
}
