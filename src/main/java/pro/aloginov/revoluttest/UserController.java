package pro.aloginov.revoluttest;

import pro.aloginov.revoluttest.model.User;
import pro.aloginov.revoluttest.protocol.UserCreationRequest;
import pro.aloginov.revoluttest.protocol.UserCreationResponse;
import pro.aloginov.revoluttest.protocol.UserDetailsResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @POST
    public UserCreationResponse createUser(UserCreationRequest request) {
        return new UserCreationResponse(userService.createUser(request.name));
    }

    @GET
    @Path("{id}")
    public UserDetailsResponse getUserDetails(@PathParam("id") int id) {
        User user = userService.getUser(id);
        return new UserDetailsResponse(user.id, user.name);
    }

}
