package com.gjchiu.currencyservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoinDeskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCoinDeskData_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/coinDesks");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updated_time", notNullValue()))
                .andExpect(jsonPath("$.currency_info", hasSize(3)))
                .andExpect(jsonPath("$.currency_info[0].code").value("USD"))
                .andExpect(jsonPath("$.currency_info[0].currencyName").value("美元"))
                .andExpect(jsonPath("$.currency_info[1].code").value("GBP"))
                .andExpect(jsonPath("$.currency_info[1].currencyName").value("英鎊"))
                .andExpect(jsonPath("$.currency_info[2].code").value("EUR"))
                .andExpect(jsonPath("$.currency_info[2].currencyName").value("歐元"));
    }
}