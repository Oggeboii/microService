package com.example.provide;

import com.example.currency.Currency;

public class SwedishCurrency implements Currency {
    @Override
    public String currencyAcronym() {
        return "SEK";
    }

    @Override
    public String currencyName(){
        return "Swedish Crowns";
    }

    @Override
    public Double currencyDiffUSD() {
        return 0.1037;
    }
}
