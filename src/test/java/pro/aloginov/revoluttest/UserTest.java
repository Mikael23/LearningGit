package pro.aloginov.revoluttest;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.google.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.aloginov.revoluttest.exception.UserNotFoundException;
import pro.aloginov.revoluttest.protocol.UserCreationRequest;
import pro.aloginov.revoluttest.protocol.UserDetailsResponse;

import static junit.framework.TestCase.assertEquals;

@RunWith(GuiceTestRunner.class)
@GuiceModules(AppServletModule.class)
public class UserTest {

    @Inject
    private UserController userController;

    @Test
    public void testUserCreation() {
        int userId = userController.createUser(new UserCreationRequest("alex")).id;
        assertEquals(0, userId);
        UserDetailsResponse userDetails = userController.getUserDetails(userId);
        assertEquals("alex", userDetails.name);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUnknownUser() {
        userController.getUserDetails(0);
    }

}
