package pro.aloginov.revoluttest.model;

public class Account {

    public final int id;
    public final Currency currency;
    public int value = 0;

    public Account(int id, Currency currency, int value) {
        this.id = id;
        this.currency = currency;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return id == account.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
