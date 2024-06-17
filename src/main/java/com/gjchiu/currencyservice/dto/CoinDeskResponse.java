package com.gjchiu.currencyservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class CoinDeskResponse {
    private Time time;
    private String disclaimer;
    private String chartName;
    private Map<String, CurrencyData> bpi;

    @Data
    public static class Time {
        private String updated;
        private String updatedISO;
        @JsonProperty("updateduk")
        private String updatedUK;
    }

    @Data
    public static class CurrencyData {
        private String code;
        private String symbol;
        private String rate;
        private String description;
        @JsonProperty("rate_float")
        private BigDecimal rate_float;
    }
}
