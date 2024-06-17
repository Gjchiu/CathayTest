package com.gjchiu.currencyservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CurrencyRequest {

    @NotBlank
    @Size(min = 3, max = 3)
    private String code;

    @NotBlank
    private String currencyName;

    @NotBlank
    private String symbol;

    @NotBlank
    private String rate;

    @NotBlank
    private String description;

    @DecimalMin(value = "0")
    @NotNull
    private BigDecimal rateFloat;

}
