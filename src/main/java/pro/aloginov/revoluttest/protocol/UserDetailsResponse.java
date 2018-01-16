package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetailsResponse {

    @JsonProperty("id")
    public final int id;
    @JsonProperty("name")
    public final String name;

    public UserDetailsResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
