package com.gjchiu.currencyservice.services;

import com.gjchiu.currencyservice.dto.CurrencyRequest;
import com.gjchiu.currencyservice.dto.UpdateCurrencyRequest;
import com.gjchiu.currencyservice.model.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> getAllCurrencies();
    Currency getCurrencyByCode(String code);
    Currency createCurrency(CurrencyRequest currencyRequest);
    Currency updateCurrency(String code, UpdateCurrencyRequest updateCurrencyRequest);
    void deleteCurrency(String code);
}
