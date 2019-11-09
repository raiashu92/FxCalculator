package com.akr.fx;

import com.akr.fx.service.ExchangeRateProvider;

import java.util.Currency;

public class TestMain {
    public static void main(String args[]){
        Currency euro = Currency.getInstance("EUR");
        System.out.println(euro.getDefaultFractionDigits());
        ExchangeRateProvider exchr = ExchangeRateProvider.getInstance();
        System.out.println("ratesMap: ");
        exchr.getRatesMap().forEach((k,v) -> System.out.println(k +" "+v));
        System.out.println("available currencies: " + exchr.getAvailableCurrencies());
    }
}
