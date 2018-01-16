package pro.aloginov.revoluttest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.aloginov.revoluttest.exception.AccountNotFoundException;
import pro.aloginov.revoluttest.model.Account;
import pro.aloginov.revoluttest.model.Currency;
import pro.aloginov.revoluttest.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.emptyList;

@Singleton
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AtomicInteger idGenerator = new AtomicInteger(0);
    //these maps might be inconsistent for a while, assume that's ok
    private final Map<Account, User> accountToUserMap = new ConcurrentHashMap<>();
    private final Map<Integer, List<Account>> userIdToAccountMap = new ConcurrentHashMap<>();
    private final Map<Integer, Account> accountMap = new ConcurrentHashMap<>();

    private final UserService userService;
    private final CurrencyService currencyService;

    @Inject
    public AccountService(
            UserService userService,
            CurrencyService currencyService) {
        this.userService = userService;
        this.currencyService = currencyService;
    }

    public final int addAccount(int userId, String currencyName, int value) {
        User user = userService.getUser(userId);
        Currency currency = currencyService.getCurrency(currencyName);
        int id = idGenerator.getAndIncrement();
        Account account = new Account(id, currency, value);
        accountMap.put(id, account);
        accountToUserMap.put(account, user);
        userIdToAccountMap.compute(user.id, (u, accList) -> {
            if (accList == null) {
                List<Account> accounts = new CopyOnWriteArrayList<>();
                accounts.add(account);
                return accounts;
            } else {
                accList.add(account);
                return accList;
            }
        });
        log.info("Created account {} for user {} with initial value {} {}", id, userId, value, currency);
        return id;
    }

    public final Account getAccount(int id) {
        Account account = accountMap.get(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        return account;
    }

    public final List<Account> getAccountsByUser(int id) {
        List<Account> accounts = userIdToAccountMap.get(id);
        return accounts == null ? emptyList() : accounts;
    }


}
