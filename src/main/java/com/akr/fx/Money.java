package com.akr.fx;

import com.akr.fx.exception.ForexException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

//@ToDo: implement serializable
public class Money implements Comparable<Money> {
    private static final Logger logger = Logger.getLogger("Money.class");
    private BigDecimal amount;
    private Currency currency;

    private Money(double value, String ccy) {
        Objects.requireNonNull(ccy, "Currency code has to be provided");
        this.currency = Currency.getInstance(ccy);
        if (value <= 0) {
            logger.log(Level.SEVERE, "Cash amount has to be specified");
            throw new ForexException("Money cannot be 0 or -ve");
        }
        this.amount = new BigDecimal(value).setScale(this.currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
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

    @Override
    public String toString() {
        return currency.getCurrencyCode() + " " + amount.toString();
    }

    public static Money cash (double value, String ccy) {
        return new Money(value, ccy);
    }

    public Money convert (String targetCcy, double finalRate) {
        if (finalRate <= 0) {
            logger.log(Level.SEVERE, "conversion rate found to be 0 or -ve, please check");
            throw new ForexException("conversion error occurred");
        }
        return new Money((this.amount.doubleValue() * finalRate), targetCcy);
    }

}
