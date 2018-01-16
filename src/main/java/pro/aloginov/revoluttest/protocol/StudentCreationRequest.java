package pro.aloginov.revoluttest.protocol;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentCreationRequest {

    public String name;
    public String city;
    public int birthYear;
    public int universityId;

  @JsonCreator
    public StudentCreationRequest(@JsonProperty("name") String name, @JsonProperty("city") String city,
                                  @JsonProperty ("BirthYear")int birthYear,@JsonProperty("UniversityId") int universityId) {
        this.name = name;
        this.city = city;
        this.birthYear = birthYear;
        this.universityId = universityId;
    }
}
