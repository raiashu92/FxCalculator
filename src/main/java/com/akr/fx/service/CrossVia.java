package com.akr.fx.service;

import com.akr.fx.FxRate;
import com.akr.fx.Money;

import java.util.Map;

//@ToDo check currencies are viable for cross or not
public class CrossVia {
    public static final String DEFAULT_CROSS_CURRENCY = "USD";

    public Money cross (String ccy1, String ccy2, double amount) {
        //ex AUD 100.00 in DKK

        Map<String, FxRate> map = ExchangeRateProvider.getInstance().getRatesMap();
        Money curency1 = Money.cash(100, ccy1);

        if (ccy1.equals(ccy2)) {
            return curency1;
        } else {
            double rate1 = (DEFAULT_CROSS_CURRENCY.equals(ccy1)) ? 1 : findRateFor(ccy1, map);
            double rate2 = (DEFAULT_CROSS_CURRENCY.equals(ccy2)) ? 1 : findRateFor(ccy2, map);
            System.out.println("rate1 is: " + rate1);
            System.out.println("rate2 is: " + rate2);
            double finalRate = (rate1 / rate2);
            Money targetCurrency = curency1.convert(ccy2, finalRate);
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
