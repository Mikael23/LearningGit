package pro.aloginov.revoluttest.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.Nullable;


public class UniversityUpdateRequest {

      @Nullable
      public final String name;
      @Nullable
      public final String city;
      @Nullable
      public final String address;

      @JsonCreator
      public UniversityUpdateRequest(
              @Nullable @JsonProperty("name") String name,
              @Nullable @JsonProperty("city") String city,
              @Nullable @JsonProperty("address") String address
      ) {
            this.name = name;
            this.city = city;
            this.address = address;
      }



}
