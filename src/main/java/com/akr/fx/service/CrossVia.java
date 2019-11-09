package com.akr.fx.service;

import com.akr.fx.FxRate;
import com.akr.fx.Money;

import java.util.Map;

//@ToDo check currencies are viable or not
public class CrossVia {
    public static final String DEFAULT_CROSS_CURRENCY = "USD";

    public Money cross (String ccy1, String ccy2, double amount) {
        //ex AUD 100.00 in DKK

        Map<String, FxRate> map = ExchangeRateProvider.getInstance().getRatesMap();
        Money curency1 = Money.cash(100, ccy1);

        double rate1 = ("USD".equals(ccy1)) ? 1 : findRateFor(ccy1, map);
        double rate2 = ("USD".equals(ccy2)) ? 1 : findRateFor(ccy2, map);
        System.out.println("rate1 is: " + rate1);
        System.out.println("rate2 is: " + rate2);
        double finalRate = (rate1 / rate2);
        Money targetCurrency = curency1.convert(ccy2, finalRate);

        return targetCurrency;
    }

    private double findRateFor (String ccy1, Map<String, FxRate> map) {
        FxRate fxRate1 = map.get(ccy1);
        if ("USD".equals(fxRate1.getTermCurrency()))
            return fxRate1.getRate();
        else
            return fxRate1.getRate() * findRateFor(fxRate1.getTermCurrency(), map);
    }

}
