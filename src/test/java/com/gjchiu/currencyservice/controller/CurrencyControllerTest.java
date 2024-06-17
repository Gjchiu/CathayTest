package com.gjchiu.currencyservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjchiu.currencyservice.dto.CurrencyRequest;
import com.gjchiu.currencyservice.dto.UpdateCurrencyRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getCurrencies_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/currency");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("EUR"))
                .andExpect(jsonPath("$[0].currencyName").value("歐元"))
                .andExpect(jsonPath("$[0].rate").value("62,168.574"))
                .andExpect(jsonPath("$[0].rateFloat").value(62168.5735))
                .andExpect(jsonPath("$[0].description").value("Euro"))
                .andExpect(jsonPath("$[1].code").value("GBP"))
                .andExpect(jsonPath("$[1].currencyName").value("英鎊"))
                .andExpect(jsonPath("$[1].rate").value("52,391.104"))
                .andExpect(jsonPath("$[1].rateFloat").value(52391.1044))
                .andExpect(jsonPath("$[1].description").value("British Pound Sterling"))
                .andExpect(jsonPath("$[2].code").value("USD"))
                .andExpect(jsonPath("$[2].currencyName").value("美元"))
                .andExpect(jsonPath("$[2].rate").value("66,861.804"))
                .andExpect(jsonPath("$[2].rateFloat").value(66861.8041))
                .andExpect(jsonPath("$[2].description").value("United States Dollar"));
    }

    @Test
    public void getCurrencyByCode_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/currency/{code}", "USD");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.currencyName").value("美元"))
                .andExpect(jsonPath("$.rate").value("66,861.804"))
                .andExpect(jsonPath("$.rateFloat").value(66861.8041))
                .andExpect(jsonPath("$.description").value("United States Dollar"));
    }

    @Test
    public void getCurrencyByCode_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/currency/{code}", "JPY");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    public void createCurrency_success() throws Exception {
        CurrencyRequest request = CurrencyRequest.builder()
                .code("JPY")
                .currencyName("日圓")
                .symbol("&yen;")
                .rate("10,286,508")
                .rateFloat(BigDecimal.valueOf(10286508))
                .description("Yen").build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/currency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("JPY"))
                .andExpect(jsonPath("$.symbol").value("&yen;"))
                .andExpect(jsonPath("$.currencyName").value("日圓"))
                .andExpect(jsonPath("$.rate").value("10,286,508"))
                .andExpect(jsonPath("$.rateFloat").value(10286508))
                .andExpect(jsonPath("$.description").value("Yen"));
    }

    @Test
    void createCurrency_illegalArgument() throws Exception {
        CurrencyRequest request = CurrencyRequest.builder()
                .code("DD")
                .rateFloat(BigDecimal.valueOf(-10286508)).build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/currency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    public void updateCurrency_success() throws Exception {
        UpdateCurrencyRequest request = UpdateCurrencyRequest.builder()
                .rate("150,00.55")
                .rateFloat(BigDecimal.valueOf(15000.55))
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/currency/{code}", "USD")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.currencyName").value("美元"))
                .andExpect(jsonPath("$.rate").value("150,00.55"))
                .andExpect(jsonPath("$.rateFloat").value(15000.55))
                .andExpect(jsonPath("$.description").value("United States Dollar"));
    }

    @Test
    public void updateCurrency_illegalArgument() throws Exception {
        UpdateCurrencyRequest request = UpdateCurrencyRequest.builder()
                .rateFloat(BigDecimal.valueOf(-110.00)).build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/currency/{code}", "USD")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCurrency_CurrencyNotFound() throws Exception {
        CurrencyRequest request = CurrencyRequest.builder()
                .rate("110.00")
                .rateFloat(BigDecimal.valueOf(110.00))
                .build();


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/currency/{code}", "AUD")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Transactional
    @Test
    public void deleteCurrency_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/currency/{code}", "USD");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCurrency_illegalArgument() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/currency/{code}", "AUD");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }
}