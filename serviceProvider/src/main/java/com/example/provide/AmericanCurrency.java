package com.example.provide;

import com.example.currency.Currency;

public class AmericanCurrency implements Currency {
    @Override
    public String currencyAcronym() {
        return "USD";
    }

    @Override
    public String currencyName() {
        return "American Dollars";
    }

    @Override
    public Double currencyDiffUSD() {
        return 1.0;
    }
}
