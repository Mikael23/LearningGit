package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UniversityCreationResponse {
    public final int id;


    @JsonCreator
    public UniversityCreationResponse(@JsonProperty("Id") int id) {
        this.id = id;
    }
}
