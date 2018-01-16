package pro.aloginov.revoluttest;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.aloginov.revoluttest.protocol.AccountCreationRequest;
import pro.aloginov.revoluttest.protocol.TransferRequest;
import pro.aloginov.revoluttest.protocol.UserCreationRequest;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.TestCase.assertEquals;

@RunWith(GuiceTestRunner.class)
@GuiceModules(AppServletModule.class)
public class TransferLoadTest {

    private static final int ACCOUNT_NUMBER = 5;
    private static final int THREAD_COUNT = 8;
    private static final int TRANSFER_COUNT = 1000000;
    private static Random random = new Random();

    @Inject
    private UserController userController;
    @Inject
    private AccountController accountController;
    @Inject
    private TransferController transferController;

    @Ignore //run this long test manually
    @Test(timeout = 30000)
    public void test() throws Exception {
        int userId = userController.createUser(new UserCreationRequest("alex")).id;
        List<Integer> accountIds = new ArrayList<>(ACCOUNT_NUMBER);
        int initialValue = 1000;
        for (int i = 0; i < ACCOUNT_NUMBER; i++) {
            accountIds.add(accountController.createAccount(new AccountCreationRequest(userId, "EUR", initialValue)).id);
        }

        ExecutorService ex = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(TRANSFER_COUNT);
        for (int i = 0; i < TRANSFER_COUNT; i++) {
            TransferRequest transferRequest = new TransferRequest(
                    accountIds.get(random.nextInt(ACCOUNT_NUMBER)),
                    accountIds.get(random.nextInt(ACCOUNT_NUMBER)),
                    "EUR",
                    random.nextInt(10)
            );
            ex.submit(() -> {
                try {
                    transferController.transfer(transferRequest);
                } catch (Exception ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        int sum = accountIds.stream()
                .map(it -> accountController.getAccountDetails(it))
                .mapToInt(it -> it.value)
                .sum();
        assertEquals(ACCOUNT_NUMBER * initialValue, sum);
        ex.shutdown();
    }

}
