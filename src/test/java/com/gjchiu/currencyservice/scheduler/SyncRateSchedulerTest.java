package com.gjchiu.currencyservice.scheduler;

import com.gjchiu.currencyservice.dto.CoinDeskResponse;
import com.gjchiu.currencyservice.model.Currency;
import com.gjchiu.currencyservice.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SyncRateSchedulerTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SyncRateScheduler syncRateScheduler;

    @Test
    public void testUpdateExchangeRate() {
        Map<String, CoinDeskResponse.CurrencyData> bpi = new HashMap<>();
        CoinDeskResponse.CurrencyData usdData = new CoinDeskResponse.CurrencyData();
        usdData.setCode("USD");
        usdData.setRate("66340.47");
        usdData.setRate_float(BigDecimal.valueOf(66340.4701));
        usdData.setSymbol("&#36;");
        usdData.setDescription("United States Dollar");
        bpi.put("USD", usdData);

        CoinDeskResponse coinDeskResponse = CoinDeskResponse.builder()
                .bpi(bpi)
                .build();

        List<Currency> currencyList = new ArrayList<>();
        Currency usdCurrency = Currency.builder()
                .code("USD")
                .currencyName("美元")
                .rate("60000.00")
                .rateFloat(BigDecimal.valueOf(60000.00))
                .lastModifiedDate(LocalDateTime.now())
                .build();
        currencyList.add(usdCurrency);

        when(restTemplate.getForObject(any(String.class), eq(CoinDeskResponse.class))).thenReturn(coinDeskResponse);
        when(currencyRepository.findAll()).thenReturn(currencyList);

        syncRateScheduler.updateExchangeRate();

        verify(restTemplate).getForObject(anyString(), eq(CoinDeskResponse.class));
        verify(currencyRepository).findAll();
        verify(currencyRepository).saveAll(anyList());
    }
}