package pro.aloginov.revoluttest.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class AppExceptionMapper implements ExceptionMapper<AppException> {

    @Override
    public Response toResponse(AppException exception) {
        return Response.serverError()
                .type(MediaType.APPLICATION_JSON)
                .entity(new Status(exception.getMessage()))
                .build();
    }

    public static class Status{

        @JsonProperty("message")
        public final String message;

        public Status(String message) {
            this.message = message;
        }

    }

}