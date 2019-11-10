package com.akr.fx.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class QueryService {
    private static final Logger logger = Logger.getLogger("QueryService.class");
    //supported query format <ccy1> <amount1> in <ccy2>
    private static final Pattern pattern = Pattern.compile("\\A[A-Za-z]{3} [\\d]*.{0,1}[\\d]{0,2} in [A-Za-z]{3}\\z");

    public static boolean verifyQuery(String query) {
        return pattern.matcher(query).matches();
    }

    public static List<String> extractData(String query, ExchangeRateProvider exchangeRateProvider) {
        Set<String> availableCurrencies = exchangeRateProvider.getAvailableCurrencies();
        String[] data = query.split(" ");
        String givenCurrency = data[0].toUpperCase();
        String targetCurrency = data[3].toUpperCase();
        String amount = data[1];

        if (!availableCurrencies.containsAll(Arrays.asList(givenCurrency, targetCurrency))) {
            //logger.log(Level.WARNING, "Unable to find rate for: " + givenCurrency + "-" + targetCurrency);
            return Arrays.asList(givenCurrency + "-" + targetCurrency);
        } else {
            return Arrays.asList(givenCurrency, targetCurrency, amount);
        }
    }
}
