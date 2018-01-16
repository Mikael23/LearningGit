package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import pro.aloginov.revoluttest.model.Currency;

public class AccountDetailsResponse {

    @JsonProperty("id")
    public final int id;
    @JsonProperty("currency")
    public final Currency currency;
    @JsonProperty("value")
    public final int value;

    public AccountDetailsResponse(int id, Currency currency, int value) {
        this.id = id;
        this.currency = currency;
        this.value = value;
    }
}
