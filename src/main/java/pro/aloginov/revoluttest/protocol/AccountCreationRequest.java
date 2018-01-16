package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCreationRequest {

    public final int userId;
    public final String currency;
    public final int value;

    @JsonCreator
    public AccountCreationRequest(
            @JsonProperty("user_id") int userId,
            @JsonProperty("currency") String currency,
            @JsonProperty("value") int value) {
        this.userId = userId;
        this.currency = currency;
        this.value = value;
    }
}
