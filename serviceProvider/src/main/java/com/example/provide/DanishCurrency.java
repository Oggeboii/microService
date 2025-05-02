package com.example.provide;

import com.example.currency.Currency;

public class DanishCurrency implements Currency {
    @Override
    public String currencyAcronym() {
        return "DKK";
    }

    @Override
    public String currencyName() {
        return "Danish Crowns";
    }

    @Override
    public Double currencyDiffUSD() {
        return 0.1521;
    }
}
