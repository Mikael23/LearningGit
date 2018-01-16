package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCreationResponse {

    @JsonProperty("id")
    public final int id;

    public UserCreationResponse(int id) {
        this.id = id;
    }

}
