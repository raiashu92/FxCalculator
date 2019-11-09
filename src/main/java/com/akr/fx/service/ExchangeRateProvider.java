package com.akr.fx.service;

import com.akr.fx.FxRate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@ToDo add a class/fn() to check data integrity in the file like xxxXXX currency format, zero rates
public class ExchangeRateProvider {
    private static final Logger logger = Logger.getLogger("ExchangeRateProvider.class");
    private Map<String, FxRate> ratesMap;
    private Set<String> availableCurrencies;
    private static ExchangeRateProvider exchangeRateProvider;
    private static final String DATA_FILE = "src//main//resources//rates.txt";

    public Map<String, FxRate> getRatesMap() {
        return ratesMap;
    }

    public Set<String> getAvailableCurrencies() {
        return availableCurrencies;
    }

    private ExchangeRateProvider() {
        this.availableCurrencies = new HashSet<>();
        ratesMap = loadRatesFromFile();
        this.availableCurrencies.addAll(ratesMap.keySet());
        this.availableCurrencies.add(CrossVia.DEFAULT_CROSS_CURRENCY);
    }

    public static ExchangeRateProvider getInstance() {
        if (Objects.isNull(exchangeRateProvider))
            exchangeRateProvider = new ExchangeRateProvider();
        return exchangeRateProvider;
    }

    private Map loadRatesFromFile () {
        Map hm = null;
        logger.info("Loading current rates for supported currencies from data file: " + DATA_FILE);

        Function<String, String> keyForRateMap = x -> {
            String baseCcy = x.split("=")[0].substring(0, 3);
            String termCcy = x.split("=")[0].substring(3, 6);
            if ("USD".equals(baseCcy) || ("EUR".equals(baseCcy) && !"USD".equals(termCcy)))
                return termCcy;
            else
                return baseCcy;
        };

        try (Stream<String> strStream = Files.lines(Paths.get(DATA_FILE))) {
            hm = strStream.collect(Collectors.toMap(
                    keyForRateMap,
                    x-> new FxRate(x.split("=")[0], x.split("=")[1]))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Objects.isNull(hm) || hm.isEmpty()) {
            logger.log(Level.SEVERE, "No data or data not in proper format. Please enter in BaseCcyTermCcy=Rate format ex: AUDUSD=0.8371");
            throw new RuntimeException("Error while reading from data file");
        }

        return hm;
    }
}
