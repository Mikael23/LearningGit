package pro.aloginov.revoluttest;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.aloginov.revoluttest.exception.CurrencyNotFoundException;
import pro.aloginov.revoluttest.exception.UserNotFoundException;
import pro.aloginov.revoluttest.protocol.AccountCreationRequest;
import pro.aloginov.revoluttest.protocol.AccountDetailsResponse;
import pro.aloginov.revoluttest.protocol.UserCreationRequest;

import javax.inject.Inject;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static junit.framework.TestCase.assertEquals;
import static pro.aloginov.revoluttest.model.Currency.eur;

@RunWith(GuiceTestRunner.class)
@GuiceModules(AppServletModule.class)
public class AccountTest {

    @Inject
    private AccountController accountController;
    @Inject
    private UserController userController;

    @Test
    public void testCreateAccount() {
        int userId = userController.createUser(new UserCreationRequest("alex")).id;
        int accountId = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 100)).id;
        AccountDetailsResponse accountDetails = accountController.getAccountDetails(accountId);
        assertEquals(eur, accountDetails.currency);
        assertEquals(100, accountDetails.value);
    }

    @Test(expected = CurrencyNotFoundException.class)
    public void testIncorrectCurrency() {
        int userId = userController.createUser(new UserCreationRequest("alex")).id;
        accountController.createAccount(new AccountCreationRequest(userId, "bucks", 100));
    }

    @Test(expected = UserNotFoundException.class)
    public void testIncorrectUser() {
        accountController.createAccount(new AccountCreationRequest(-1, "EUR", 100));
    }

    @Test
    public void testMultipleAccountsForUser() {
        int userId = userController.createUser(new UserCreationRequest("alex")).id;
        assertEquals(emptyList(), accountController.getUserAccountIds(userId));
        int accountId1 = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 100)).id;
        int accountId2 = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 200)).id;
        assertEquals(asList(accountId1, accountId2), accountController.getUserAccountIds(userId));
    }

}
