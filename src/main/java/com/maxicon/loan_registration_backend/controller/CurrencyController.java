package com.maxicon.loan_registration_backend.controller;

import com.maxicon.loan_registration_backend.service.CurrencyService;
import com.maxicon.loan_registration_backend.dto.ExchangeRateApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/currencies")
@CrossOrigin(origins = "http://localhost:4200")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/list")
    public Map<String, String> getCurrencyList() {
        return currencyService.getCachedCurrencyData();
    }

    @GetMapping("/exchange-rate")
    public ExchangeRateApiResponse getExchangeRate(@RequestParam String currencyCode,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return currencyService.getExchangeRate(currencyCode, start, end);
    }
}

