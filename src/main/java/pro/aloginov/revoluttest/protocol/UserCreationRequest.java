package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserCreationRequest {

    public final String name;

    @JsonCreator
    public UserCreationRequest(
            @JsonProperty("user_name") String name) {
        checkNotNull(name);
        this.name = name;
    }
}
