package com.akr.fx.service;

import com.akr.fx.FxRate;
import com.akr.fx.Money;

import java.util.Map;
import java.util.logging.Logger;

public class CrossVia {
    private static final Logger logger = Logger.getLogger("CrossVia.class");
    public static final String DEFAULT_CROSS_CURRENCY = "USD";
    private static Map<String, FxRate> map = ExchangeRateProvider.getInstance().getRatesMap();

    public static Money cross(Money givenMoney, String targetCurrency) {
        String sourceCurrency = givenMoney.getCurrency();
        if (sourceCurrency.equals(targetCurrency)) {
            return givenMoney;
        } else {
            double rate1 = (DEFAULT_CROSS_CURRENCY.equals(sourceCurrency)) ? 1 : findRateFor(sourceCurrency, map);
            double rate2 = (DEFAULT_CROSS_CURRENCY.equals(targetCurrency)) ? 1 : findRateFor(targetCurrency, map);
            double finalRate = (rate1 / rate2);
            Money targetMoney = givenMoney.convertTo(targetCurrency, finalRate);
            return targetMoney;
        }
    }

    private static double findRateFor(String ccy1, Map<String, FxRate> map) {
        FxRate fxRate1 = map.get(ccy1);
        if (DEFAULT_CROSS_CURRENCY.equals(fxRate1.getTermCurrency()))
            return fxRate1.getRate();
        else
            return fxRate1.getRate() * findRateFor(fxRate1.getTermCurrency(), map);
    }

}
