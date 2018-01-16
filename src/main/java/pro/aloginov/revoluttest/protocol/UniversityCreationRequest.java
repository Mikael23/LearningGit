package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class UniversityCreationRequest {

      public final String name;
      public final String city;
      public final String address;

      @JsonCreator
      public UniversityCreationRequest(
              @JsonProperty("name") String name,
              @JsonProperty("city") String city,
              @JsonProperty("address") String address
      ) {
            this.name = name;
            this.city = city;
            this.address = address;
      }



}
