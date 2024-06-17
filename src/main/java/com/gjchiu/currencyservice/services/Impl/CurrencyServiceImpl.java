package com.gjchiu.currencyservice.services.Impl;

import com.gjchiu.currencyservice.dto.CurrencyRequest;
import com.gjchiu.currencyservice.dto.UpdateCurrencyRequest;
import com.gjchiu.currencyservice.model.Currency;
import com.gjchiu.currencyservice.repository.CurrencyRepository;
import com.gjchiu.currencyservice.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    @Transactional
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAllByOrderByCodeAsc();
    }

    @Override
    @Transactional
    public Currency getCurrencyByCode(String code) {
        return currencyRepository.findByCode(code).orElse(null);
    }

    @Override
    @Transactional
    public Currency createCurrency(CurrencyRequest currencyRequest) {
        Currency currency = Currency.builder()
                .code(currencyRequest.getCode())
                .currencyName(currencyRequest.getCurrencyName())
                .symbol(currencyRequest.getSymbol())
                .description(currencyRequest.getDescription())
                .rate(currencyRequest.getRate())
                .rateFloat(currencyRequest.getRateFloat()).build();
        currency = currencyRepository.save(currency);

        return currency;
    }

    @Override
    @Transactional
    public Currency updateCurrency(String code, UpdateCurrencyRequest updateCurrencyRequest) {
        Currency currency = currencyRepository.findByCode(code).orElseThrow();
        if (updateCurrencyRequest.getCurrencyName() != null) {
            currency.setCurrencyName(updateCurrencyRequest.getCurrencyName());
        }

        if (updateCurrencyRequest.getSymbol() != null) {
            currency.setSymbol(updateCurrencyRequest.getSymbol());
        }

        if (updateCurrencyRequest.getRate() != null) {
            currency.setRate(updateCurrencyRequest.getRate());
        }

        if (updateCurrencyRequest.getDescription() != null) {
            currency.setDescription(updateCurrencyRequest.getDescription());
        }

        if (updateCurrencyRequest.getRateFloat() != null) {
            currency.setRateFloat(updateCurrencyRequest.getRateFloat());
        }

        currencyRepository.save(currency);

        return currency;
    }

    @Override
    @Transactional
    public void deleteCurrency(String code) {
        currencyRepository.deleteByCode(code);
    }
}
