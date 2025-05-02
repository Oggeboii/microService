import com.example.currency.Currency;
import com.example.provide.AmericanCurrency;
import com.example.provide.DanishCurrency;
import com.example.provide.FortniteCurrency;
import com.example.provide.SwedishCurrency;

module com.example.serviceProvider {
    requires com.example.service;
    provides Currency with SwedishCurrency, AmericanCurrency, FortniteCurrency, DanishCurrency;
}
