package com.gjchiu.currencyservice.controller;

import com.gjchiu.currencyservice.services.CoinDeskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "CoinDesk API", description = "Get Data from CoinDesk API ")
public class CoinDeskController {

    private final CoinDeskService coindeskService;

    public CoinDeskController(CoinDeskService coindeskService) {
        this.coindeskService = coindeskService;
    }

    @GetMapping("/coinDesks")
    @Operation(summary = "呼叫coindesk的API，並進行資料轉換", description = "呼叫coindesk的API，並進行資料轉換")
    public ResponseEntity<Map<String, Object>> getCoinDeskData() throws Exception {
        Map<String, Object> coinDeskData = coindeskService.getCoinDeskData();
        return ResponseEntity.status(HttpStatus.OK).body(coinDeskData);
    }
}
