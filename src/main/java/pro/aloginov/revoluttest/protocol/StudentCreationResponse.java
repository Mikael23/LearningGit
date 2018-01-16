package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentCreationResponse {

    public final int id;




    @JsonCreator
    public StudentCreationResponse(@JsonProperty("Id") int id) {
        this.id = id;
    }
}
