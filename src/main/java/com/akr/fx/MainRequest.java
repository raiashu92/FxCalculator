package com.akr.fx;

import com.akr.fx.service.CrossVia;
import com.akr.fx.service.ExchangeRateProvider;
import com.akr.fx.service.QueryService;

import java.util.List;
import java.util.Scanner;

public class MainRequest {
    public static void main (String args[]) {
        ExchangeRateProvider exchangeRateProvider = ExchangeRateProvider.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input has to be in this format: \n <ccy1> <amount1> in <ccy2>");
        System.out.println("type exit to close the program.");

        boolean shouldContinue;
        String query;
        List<String> data;
        while (scanner.hasNext()) {
            query = scanner.nextLine();
            if (!"exit".equalsIgnoreCase(query)) {
                shouldContinue = QueryService.verifyQuery(query);
                if (shouldContinue) {
                    data = QueryService.extractData(query, exchangeRateProvider);
                    if (data.size() == 1) {
                        System.out.println("Unable to find rate for " + data.get(0));
                        continue;
                    }
                    Money sourceMoney = Money.cash(Double.parseDouble(data.get(2)), data.get(0));
                    Money targetMoney = CrossVia.cross(sourceMoney, data.get(1));
                    System.out.println(sourceMoney + " = " + targetMoney);
                } else {
                    System.out.println("Query not in supported format \n supported query format <ccy1> <amount1> in <ccy2>");
                }
            } else {
                System.out.println("Exiting ...");
                break;
            }
        }
    }
}
