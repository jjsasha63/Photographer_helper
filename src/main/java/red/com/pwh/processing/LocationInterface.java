package red.com.pwh.processing;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.MalformedURLException;


public interface LocationInterface {

    String get_address(String input) throws IOException;


    String get_address(Double latitude,Double longitude) throws IOException;

    Double[] get_param(String input) throws IOException;


    String get_timezone(String input) throws IOException;


    String get_timezone(Double latitude,Double longitude) throws IOException;

}
