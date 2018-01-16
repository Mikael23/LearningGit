package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {

    public final int fromId;
    public final int toId;
    public final String currency;
    public final int value;

    @JsonCreator
    public TransferRequest(
            @JsonProperty("from") int fromId,
            @JsonProperty("to") int toId,
            @JsonProperty("currency") String currency,
            @JsonProperty("value")int value) {
        this.fromId = fromId;
        this.toId = toId;
        this.currency = currency;
        this.value = value;
    }

}
