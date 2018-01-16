package pro.aloginov.revoluttest;

import pro.aloginov.revoluttest.model.Currency;

import javax.inject.Singleton;

@Singleton
public class CurrencyService {

    public Currency getCurrency(String name) {
        try {
            return Currency.valueOf(name.toLowerCase());
        } catch (Exception e) {
            return Currency.usd;
        }
    }

}
