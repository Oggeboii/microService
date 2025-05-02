package com.example.provide;

import com.example.currency.Currency;

public class FortniteCurrency implements Currency {


    @Override
    public String currencyAcronym() {
        return "VB";
    }

    @Override
    public String currencyName() {
        return "VBucks";
    }

    @Override
    public Double currencyDiffUSD() {
        return 0.0645;
    }
}
