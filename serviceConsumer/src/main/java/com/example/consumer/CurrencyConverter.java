package com.example.consumer;

import com.example.currency.Currency;

import java.util.Scanner;
import java.util.ServiceLoader;

public class CurrencyConverter {

    public Scanner scanner;
    private CurrencyInfo currentCurrency;

    public CurrencyConverter() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        CurrencyConverter currencyConverter = new CurrencyConverter();
        currencyConverter.run();
    }

    public void run() {
        int defaultAmount = 1;
        boolean showMenu = true;

        while (showMenu) {
            showMenu = displayMenu(defaultAmount);
        }
    }

    private boolean displayMenu(int defaultAmount) {
        ServiceLoader<Currency> currencyServiceLoader = ServiceLoader.load(Currency.class);
        var currencies = currencyServiceLoader.stream()
                .map(ServiceLoader.Provider::get)
                .toList();

        for (int i = 0; i < currencies.size(); i++) {
            System.out.println((i + 1) + ". " + currencies.get(i).currencyName());
        }

        String menuSelected = scanner.nextLine();
        try {
            int selectedIndex = Integer.parseInt(menuSelected) - 1;
            if (selectedIndex >= 0 && selectedIndex < currencies.size()) {
                Currency selectedCurrency = currencies.get(selectedIndex);
                currentCurrency = new CurrencyInfo(selectedCurrency.currencyName(), selectedCurrency.currencyAcronym(), selectedCurrency.currencyDiffUSD());
                displayExchangeRates(currentCurrency, defaultAmount, currencies);
                return displayOptions(defaultAmount, currencies);
            } else {
                System.out.println("Invalid selection. Please try again.");
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection. Please try again.");
            return true;
        }
    }

    private boolean displayOptions(int defaultAmount, java.util.List<Currency> currencies) {
        boolean showOptions = true;
        while (showOptions) {
            System.out.println("1. Back to menu");
            System.out.println("2. Change amount");

            String optionSelected = scanner.nextLine();
            switch (optionSelected) {
                case "1":
                    return true;
                case "2":
                    try {
                        System.out.println("Enter amount to change: ");
                        defaultAmount = Integer.parseInt(scanner.nextLine());
                        displayExchangeRates(currentCurrency, defaultAmount, currencies);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount. Please enter a valid number.");
                    }
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
        return false;
    }

    public void displayExchangeRates(CurrencyInfo currencyInfo, int amount, java.util.List<Currency> currencies) {
        System.out.println("Exchange rates for " + currencyInfo.name + " (" + currencyInfo.acronym + "):");
        for (Currency currency : currencies) {
            CurrencyInfo info = new CurrencyInfo(currency.currencyName(), currency.currencyAcronym(), currency.currencyDiffUSD());
            System.out.println(amount + " " + currencyInfo.acronym + " : " + String.format("%.3f", (amount * currencyInfo.exchangeRate / info.exchangeRate)) + " " + info.acronym);
        }
    }

    private static class CurrencyInfo {
        String name;
        String acronym;
        double exchangeRate;

        CurrencyInfo(String name, String acronym, double exchangeRate) {
            this.name = name;
            this.acronym = acronym;
            this.exchangeRate = exchangeRate;
        }
    }
}
