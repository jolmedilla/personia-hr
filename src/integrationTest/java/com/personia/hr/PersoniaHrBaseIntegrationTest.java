package com.personia.hr;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

interface PersoniaHrBaseIntegrationTest {
    
    String  FIRST_SAMPLE_INPUT_HIERARCHY = "{ " +
            " \"Pete\": \"Nick\"," +
            " \"Barbara\": \"Nick\"," +
            " \"Nick\": \"Sophie\"," +
            " \"Sophie\": \"Jonas\"" +
            "}";

    String  FIRST_EXPECTED_OUTPUT = "{" +
            "          \"Jonas\": {" +
            "              \"Sophie\": {" +
            "                  \"Nick\": {" +
            "                      \"Pete\": {}," +
            "                      \"Barbara\": {}" +
            "                  }" +
            "} }" +
            "}";

    String  SECOND_SAMPLE_INPUT_HIERARCHY = "{ " +
            " \"Juan\": \"Pedro\"," +
            " \"Sergi\": \"Pedro\"," +
            " \"Pedro\": \"Lupe\"," +
            " \"Lupe\": \"Helena\"" +
            "}";

    String  SECOND_EXPECTED_OUTPUT = "{" +
            "          \"Helena\": {" +
            "              \"Lupe\": {" +
            "                  \"Pedro\": {" +
            "                      \"Juan\": {}," +
            "                      \"Sergi\": {}" +
            "                  }" +
            "} }" +
            "}";

    String  TWO_SUPERVISORS_SAMPLE ="{" +
            " \"Pete\": \"Nick\" ," +
            " \"Pete\": \"Barbara\"}";

    String  LOOP_IN_HIERARCHY ="{\"Andrew\":\"Sophie\",\"Pete\":\"Barbara\",\"Barbara\":\"Nick\",\"Nick\":\"Pete\",\"Sophie\":\"Juan\"}";

    String  MULTIPLE_ROOT_SAMPLE = "{\"Pete\":\"Barbara\",\"Nick\":\"Sophie\"}";

    String USERNAME = "juan";

    String PASSWORD = "0hmyG00D";

    MockMvc getMockMvc();

    default HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
