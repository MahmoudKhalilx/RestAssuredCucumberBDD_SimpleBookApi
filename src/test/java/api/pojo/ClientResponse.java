package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientResponse {

    private String accessToken;
    private String error;

    public String getAccessToken() {
        return accessToken;
    }

    public String getError() {
        return error;
    }
}
