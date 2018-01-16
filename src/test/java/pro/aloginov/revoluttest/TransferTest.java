package pro.aloginov.revoluttest;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.aloginov.revoluttest.exception.TransferException;
import pro.aloginov.revoluttest.protocol.AccountCreationRequest;
import pro.aloginov.revoluttest.protocol.TransferRequest;
import pro.aloginov.revoluttest.protocol.UserCreationRequest;

import javax.inject.Inject;

import static junit.framework.TestCase.assertEquals;

@RunWith(GuiceTestRunner.class)
@GuiceModules(AppServletModule.class)
public class TransferTest {

    @Inject
    private UserController userController;
    @Inject
    private AccountController accountController;
    @Inject
    private TransferController transferController;

    @Test
    public void testSuccessfulTransfer() {
        int userId = userController.createUser(new UserCreationRequest("alex")).id;
        int accountId1 = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 100)).id;
        int accountId2 = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 200)).id;
        transferController.transfer(new TransferRequest(accountId1, accountId2, "EUR", 15));
        assertEquals(85, accountController.getAccountDetails(accountId1).value);
        assertEquals(215, accountController.getAccountDetails(accountId2).value);
    }

    @Test(expected = TransferException.class)
    public void testSameAccountTransfer() {
        transferController.transfer(new TransferRequest(1, 1, "RUB", 15));
    }

    @Test(expected = TransferException.class)
    public void testWrongValue() {
        transferController.transfer(new TransferRequest(1, 2, "RUB", -1));
    }

    @Test(expected = TransferException.class)
    public void testIncorrectRequestCurrency() {
        int userId = userController.createUser(new UserCreationRequest("alex")).id;
        int accountId1 = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 100)).id;
        int accountId2 = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 200)).id;
        transferController.transfer(new TransferRequest(accountId1, accountId2, "RUB", 15));
    }

    @Test(expected = TransferException.class)
    public void testDifferentAccountCurrencies() {
        int userId = userController.createUser(new UserCreationRequest("alex")).id;
        int accountId1 = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 100)).id;
        int accountId2 = accountController.createAccount(new AccountCreationRequest(userId, "RUB", 200)).id;
        transferController.transfer(new TransferRequest(accountId1, accountId2, "EUR", 15));
    }

    @Test(expected = TransferException.class)
    public void testNotEnoughMoney() {
        int userId = userController.createUser(new UserCreationRequest("alex")).id;
        int accountId1 = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 100)).id;
        int accountId2 = accountController.createAccount(new AccountCreationRequest(userId, "EUR", 200)).id;
        transferController.transfer(new TransferRequest(accountId1, accountId2, "EUR", 150));
    }

}
