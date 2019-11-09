package com.akr.fx.service;

public class FxRate {
    private String baseCurrency;
    private String termCurrency;
    private double rate;

    @Override
    public String toString() {
        return "FxRate is {" +
                "1 " + baseCurrency +
                " = " + rate + " "+ termCurrency +
                "}";
    }

    public FxRate(String ccyPair, String rate) {
        this.baseCurrency = ccyPair.substring(0, 3);
        this.termCurrency = ccyPair.substring(3, 6);
        if (this.baseCurrency.equals(CrossVia.DEFAULT_CROSS_CURRENCY)) {
            String temp = this.baseCurrency;
            this.baseCurrency = this.termCurrency;
            this.termCurrency = temp;
            this.rate = 1 / Double.parseDouble(rate);
        } else {
            this.rate = Double.parseDouble(rate);
        }
    }
}
