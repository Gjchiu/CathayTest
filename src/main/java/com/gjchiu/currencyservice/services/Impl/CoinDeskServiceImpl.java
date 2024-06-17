package com.gjchiu.currencyservice.services.Impl;

import com.gjchiu.currencyservice.dto.CoinDeskResponse;
import com.gjchiu.currencyservice.model.Currency;
import com.gjchiu.currencyservice.repository.CurrencyRepository;
import com.gjchiu.currencyservice.services.CoinDeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.gjchiu.currencyservice.utils.CommonUtil.convertISOToTaipeiTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CoinDeskServiceImpl implements CoinDeskService {

    private final CurrencyRepository currencyRepository;

    private final RestTemplate restTemplate;

    private final MessageSource messageSource;

    private final String COINDESK_API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    public Map<String, Object> getCoinDeskData() {
        CoinDeskResponse coinDeskResponse = restTemplate.getForObject(COINDESK_API_URL, CoinDeskResponse.class);

        Map<String, Object> transformedData = new HashMap<>();
        transformedData.put("updated_time", convertISOToTaipeiTime(coinDeskResponse.getTime().getUpdatedISO()));

        List<Map<String, Object>> currencyInfo = new LinkedList<>();

        for (Map.Entry<String, CoinDeskResponse.CurrencyData> entry : coinDeskResponse.getBpi().entrySet()) {
            Currency currency = currencyRepository.findByCode(entry.getKey()).orElse(null);
            Map<String, Object> info = new HashMap<>();
            info.put("code", entry.getKey());
            if (currency != null) {
                //i18n
                String currencyName = messageSource.getMessage(currency.getCode(), null, LocaleContextHolder.getLocale());
                info.put("currencyName", currencyName);
            }
            info.put("rate", entry.getValue().getRate());
            info.put("rate_float", entry.getValue().getRate_float());
            currencyInfo.add(info);
        }

        transformedData.put("currency_info", currencyInfo);
        return transformedData;

    }
}
