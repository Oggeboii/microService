package com.example.consumer;

import com.example.currency.Currency;

import java.util.Scanner;
import java.util.ServiceLoader;

public class CurrencyConverter {

    public Scanner scanner;
    private CurrencyInfo usdInfo;
    private CurrencyInfo sekInfo;
    private CurrencyInfo dkkInfo;
    private CurrencyInfo vbInfo;
    private CurrencyInfo currentCurrency;

    public CurrencyConverter() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        CurrencyConverter currencyConverter = new CurrencyConverter();
        currencyConverter.run();
    }

    public void run() {
        ServiceLoader<Currency> currencyServiceLoader = ServiceLoader.load(Currency.class);

        var currencies = currencyServiceLoader.stream()
                .map(ServiceLoader.Provider::get)
                .toList();

        for (Currency currency : currencies) {
            String currencyName = currency.getClass().getSimpleName();
            CurrencyInfo currencyInfo = new CurrencyInfo(currency.currencyName(), currency.currencyAcronym(), currency.currencyDiffUSD());

            if (currencyName.startsWith("American")) {
                usdInfo = currencyInfo;
            } else if (currencyName.startsWith("Swedish")) {
                sekInfo = currencyInfo;
            } else if (currencyName.startsWith("Danish")) {
                dkkInfo = currencyInfo;
            } else if (currencyName.startsWith("Fortnite")) {
                vbInfo = currencyInfo;
            }
        }

        int defaultAmount = 1;
        boolean showMenu = true;

        while (showMenu) {
            showMenu = displayMenu(defaultAmount);
        }
    }

    private boolean displayMenu(int defaultAmount) {
        System.out.println("1. " + usdInfo.name);
        System.out.println("2. " + sekInfo.name);
        System.out.println("3. " + dkkInfo.name);
        System.out.println("4. " + vbInfo.name);

        String menuSelected = scanner.nextLine();
        switch (menuSelected) {
            case "1":
                displayExchangeRates(usdInfo, defaultAmount);
                currentCurrency = usdInfo;
                return displayOptions(defaultAmount);
            case "2":
                displayExchangeRates(sekInfo, defaultAmount);
                currentCurrency = sekInfo;
                return displayOptions(defaultAmount);
            case "3":
                displayExchangeRates(dkkInfo, defaultAmount);
                currentCurrency = dkkInfo;
                return displayOptions(defaultAmount);
            case "4":
                displayExchangeRates(vbInfo, defaultAmount);
                currentCurrency = vbInfo;
                return displayOptions(defaultAmount);
            default:
                System.out.println("Invalid selection. Please try again.");
                return true;
        }
    }

    private boolean displayOptions(int defaultAmount) {
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
                        displayExchangeRates(currentCurrency, defaultAmount);
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

    public void displayExchangeRates(CurrencyInfo currencyInfo, int amount) {
        System.out.println("Exchange rates for " + currencyInfo.name + " (" + currencyInfo.acronym + "):");
        System.out.println(amount + " " + currencyInfo.acronym + " : " + String.format("%.3f" , (amount * currencyInfo.exchangeRate / usdInfo.exchangeRate)) + " " + usdInfo.acronym);
        System.out.println(amount + " " + currencyInfo.acronym + " : " + String.format("%.3f", (amount * currencyInfo.exchangeRate / sekInfo.exchangeRate)) + " " + sekInfo.acronym);
        System.out.println(amount + " " + currencyInfo.acronym + " : " + String.format("%.3f", (amount * currencyInfo.exchangeRate / dkkInfo.exchangeRate)) + " " + dkkInfo.acronym);
        System.out.println(amount + " " + currencyInfo.acronym + " : " + String.format("%.3f", (amount * currencyInfo.exchangeRate / vbInfo.exchangeRate)) + " " + vbInfo.acronym);
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
