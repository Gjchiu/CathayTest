package com.gjchiu.currencyservice.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateCurrencyRequest {

    private String currencyName;

    private String symbol;

    private String rate;

    private String description;

    @DecimalMin(value = "0")
    private BigDecimal rateFloat;

}
