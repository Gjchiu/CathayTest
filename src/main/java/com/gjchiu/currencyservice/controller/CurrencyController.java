package com.gjchiu.currencyservice.controller;

import com.gjchiu.currencyservice.dto.CurrencyRequest;
import com.gjchiu.currencyservice.dto.UpdateCurrencyRequest;
import com.gjchiu.currencyservice.model.Currency;
import com.gjchiu.currencyservice.services.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
@Tag(name = "Currency API", description = "Operations for managing Currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    @Operation(summary = "取得全部貨幣資料", description = "取得全部貨幣資料並依幣別排序")
    public ResponseEntity<List<Currency>> getCurrencies() {
        List<Currency> currencyList = currencyService.getAllCurrencies();
        return ResponseEntity.status(HttpStatus.OK).body(currencyList);
    }

    @GetMapping("/{code}")
    @Operation(summary = "取得貨幣資料", description = "藉由幣別取得貨幣資料")
    public ResponseEntity<Currency> getCurrencyByCode(@PathVariable @NotNull String code) {

        Currency currency = currencyService.getCurrencyByCode(code);
        if(currency != null){
            return ResponseEntity.status(HttpStatus.OK).body(currency);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary = "新增貨幣資料", description = "新增貨幣資料")
    public ResponseEntity<Currency> createCurrency(@RequestBody @Valid CurrencyRequest currencyRequest) {
        Currency currency = currencyService.createCurrency(currencyRequest);

        if(currency != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(currency);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{code}")
    @Operation(summary = "修改貨幣資料", description = "修改貨幣資料")
    public ResponseEntity<Currency> updateCurrency(@PathVariable @NotNull String code,
                                                   @RequestBody @Valid UpdateCurrencyRequest updateCurrencyRequest) {
        Currency currency = currencyService.getCurrencyByCode(code);

        if (currency == null) {
            return ResponseEntity.noContent().build();
        }

        currency = currencyService.updateCurrency(code, updateCurrencyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(currency);
    }

    @DeleteMapping("/{code}")
    @Operation(summary = "刪除貨幣資料", description = "刪除貨幣資料")
    public ResponseEntity<Currency> deleteCurrency(@PathVariable @NotNull String code) {
        currencyService.deleteCurrency(code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
