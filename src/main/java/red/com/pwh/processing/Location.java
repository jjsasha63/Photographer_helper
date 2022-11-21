package red.com.pwh.processing;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;


public class Location implements LocationInterface {

    @Value("positionstack.api.key")
    private String key;

    @Value("positionstack.api")
    private String api;

    @Value("positionstack.api.query")
    private String query;

    @Value("positionstack.api.limit")
    private String limit;

    @Value("positionstack.api.timezone")
    private String timezone;

    @Value("positionstack.api.fields")
    private String fields;

    @Value("positionstack.api.fields.ll")
    private String lat_long;


    @Override
    public String get_address(String input) throws JsonProcessingException {
        String url = api+key+query+input+limit+fields;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        return jsonNode.get("label").asText() + "\n" + jsonNode.get("street").asText() + ", " + jsonNode.get("number").asText() + ", " + jsonNode.get("postal_code");
    }

    @Override
    public String get_address(Double latitude, Double longitude) throws JsonProcessingException {
        String url = api+key+query+latitude+","+longitude+limit+fields;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        return jsonNode.get("label").asText() + "\n" + jsonNode.get("street").asText() + ", " + jsonNode.get("number").asText() + ", " + jsonNode.get("postal_code");
    }

    @Override
    public Double[] get_param(String input) throws JsonProcessingException {
        String url = api+key+query+input+limit+lat_long;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        return new Double[]{jsonNode.get("latitude").asDouble(),jsonNode.get("longitude").asDouble()};
    }

    @Override
    public String get_timezone(String input) throws JsonProcessingException {
        String url = api+key+query+input+timezone+limit;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        return jsonNode.get("name").asText();
    }

    @Override
    public String get_timezone(Double latitude, Double longitude) throws JsonProcessingException {
        String url = api+key+query+latitude+","+longitude+timezone+limit;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(url);
        return jsonNode.get("name").asText();
    }
}
