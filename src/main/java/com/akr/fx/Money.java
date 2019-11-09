package com.akr.fx;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

//@ToDo: implement serializable, add fraction check on amount
public class Money implements Comparable<Money> {
    private BigDecimal amount;
    private Currency currency;

    private Money(double value, String ccy) {
        Objects.requireNonNull(ccy, "Currency code has to be provided");
        this.currency = Currency.getInstance(ccy);
        Objects.requireNonNull(value, "Cash amount has to be specified");
        this.amount = new BigDecimal(value).setScale(this.currency.getDefaultFractionDigits());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) &&
                currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public int compareTo(Money that) {
        Objects.requireNonNull(that);

        int isCurrencySame = this.currency.getCurrencyCode().compareTo(that.currency.getCurrencyCode());
        if (isCurrencySame == 0)
            return this.amount.compareTo(that.amount);
        return isCurrencySame;
    }

    public Money cash (double value, String ccy) {
        return new Money(value, ccy);
    }

}
