package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCreationResponse {

    @JsonProperty("account_id")
    public final int id;

    public  AccountCreationResponse(int id) {
        this.id = id;
    }
}
