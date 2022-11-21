package red.com.pwh.processing;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface LocationInterface {

    String get_address(String input) throws JsonProcessingException;

    String get_address(Double latitude,Double longitude) throws JsonProcessingException;

    Double[] get_param(String input) throws JsonProcessingException;

    String get_timezone(String input) throws JsonProcessingException;

    String get_timezone(Double latitude,Double longitude) throws JsonProcessingException;

}
