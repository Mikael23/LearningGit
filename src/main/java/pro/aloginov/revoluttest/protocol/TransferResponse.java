package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

import static java.lang.System.currentTimeMillis;

public class TransferResponse {

    @JsonProperty("execution_date")
    public final long executionDate = currentTimeMillis();

}
