package com.akr.fx.service;

import com.akr.fx.FxRate;
import com.akr.fx.Money;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

//@ToDo check currencies are viable for cross or not
public class CrossVia {
    private static final Logger logger = Logger.getLogger("CrossVia.class");
    public static final String DEFAULT_CROSS_CURRENCY = "USD";

    public Money cross (String ccy1, String ccy2, double amount) {

        Map<String, FxRate> map = ExchangeRateProvider.getInstance().getRatesMap();
        Set<String> availableCurrencies = ExchangeRateProvider.getInstance().getAvailableCurrencies();
        if (!availableCurrencies.containsAll(Arrays.asList(ccy1, ccy2))) {
            logger.log(Level.WARNING, "Unable to find rate for: " + ccy1 + "-" + ccy2);
            throw new RuntimeException("Forex rate not available");
        }

        Money givenCurrency = Money.cash(100, ccy1);

        if (ccy1.equals(ccy2)) {
            return givenCurrency;
        } else {
            double rate1 = (DEFAULT_CROSS_CURRENCY.equals(ccy1)) ? 1 : findRateFor(ccy1, map);
            double rate2 = (DEFAULT_CROSS_CURRENCY.equals(ccy2)) ? 1 : findRateFor(ccy2, map);
            double finalRate = (rate1 / rate2);
            Money targetCurrency = givenCurrency.convert(ccy2, finalRate);
            return targetCurrency;
        }
    }

    private double findRateFor (String ccy1, Map<String, FxRate> map) {
        FxRate fxRate1 = map.get(ccy1);
        if (DEFAULT_CROSS_CURRENCY.equals(fxRate1.getTermCurrency()))
            return fxRate1.getRate();
        else
            return fxRate1.getRate() * findRateFor(fxRate1.getTermCurrency(), map);
    }

}
