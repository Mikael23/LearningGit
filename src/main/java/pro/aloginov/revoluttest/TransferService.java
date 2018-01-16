package pro.aloginov.revoluttest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.aloginov.revoluttest.exception.TransferException;
import pro.aloginov.revoluttest.model.Account;
import pro.aloginov.revoluttest.model.Currency;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransferService {

    private final Logger transferLog = LoggerFactory.getLogger("transfer");

    private final AccountService accountService;
    private final CurrencyService currencyService;

    @Inject
    public TransferService(
            AccountService accountService,
            CurrencyService currencyService) {
        this.accountService = accountService;
        this.currencyService = currencyService;
    }

    public void transfer(int fromId, int toId, String currencyName, int value) {
        if (fromId == toId) {
            throw new TransferException("Source and destination accounts coincide");
        }
        if (value <= 0) {
            throw new TransferException(("Value should be positive"));
        }
        Account from = accountService.getAccount(fromId);
        Account to = accountService.getAccount(toId);
        Currency currency = currencyService.getCurrency(currencyName);
        if (from.currency != currency) {
            throw new TransferException(currencyException(from.id, from.currency, currency));
        }
        if (to.currency != currency) {
            throw new TransferException(currencyException(to.id, to.currency, currency));
        }
        if (from.value < value) {
            throw new TransferException("Account " + from.id + " doesn't have enough value");
        }
        checkValue(from, value);
        synchronized (from.id > to.id ? from : to) {
            checkValue(from, value);
            synchronized (from.id > to.id ? to : from) {
                from.value -= value;
                to.value += value;
                transferLog.info("Transaction {} -> {} {} {}", from.id, to.id, value, currency);
            }
        }
    }

    private static void checkValue(Account account, int value) {
        if (account.value < value) {
            throw new TransferException("Account " + account.id + " doesn't have enough value");
        }
    }

    private static String currencyException(int accountId, Currency accountCurrency, Currency currency) {
        return String.format("Account %s currency '%s' differs from requested currency '%s'",
                accountId, accountCurrency, currency);
    }

}
