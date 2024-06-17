package com.gjchiu.currencyservice.scheduler;

import com.gjchiu.currencyservice.dto.CoinDeskResponse;
import com.gjchiu.currencyservice.model.Currency;
import com.gjchiu.currencyservice.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SyncRateScheduler {

    private final CurrencyRepository currencyRepository;

    private final RestTemplate restTemplate;

    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void updateExchangeRate() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        CoinDeskResponse coinDeskResponse = restTemplate.getForObject(url, CoinDeskResponse.class);
        Map<String, Currency> currencyMap = currencyRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Currency::getCode, currency -> currency));

        for (Map.Entry<String, CoinDeskResponse.CurrencyData> entry : coinDeskResponse.getBpi().entrySet()) {
            Currency currency = currencyMap.get(entry.getKey());
            currency.setRate(entry.getValue().getRate());
            currency.setRateFloat(entry.getValue().getRate_float());
            currency.setLastSyncDate(LocalDateTime.now());
            currencyMap.put(entry.getKey(), currency);
        }

        currencyRepository.saveAll(new ArrayList<>(currencyMap.values()));
    }
}
