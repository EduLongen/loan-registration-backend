package com.maxicon.loan_registration_backend.service;

import com.maxicon.loan_registration_backend.dto.CurrencyApiResponse;
import com.maxicon.loan_registration_backend.dto.ExchangeRateApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String CURRENCY_API_URL = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/Moedas?$format=json";
    private static final String EXCHANGE_RATE_URL_TEMPLATE = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoMoedaPeriodo(moeda='%s',dataInicial='%s',dataFinalCotacao='%s')?$format=json";

    // Cached currency data to avoid repetitive API calls
    private Map<String, String> currencyCache = new HashMap<>();

    public Map<String, String> getCachedCurrencyData() {
        if (currencyCache.isEmpty()) {
            refreshCurrencyCache();
        }
        return currencyCache;
    }

    public void refreshCurrencyCache() {
        CurrencyApiResponse response = restTemplate.getForObject(CURRENCY_API_URL, CurrencyApiResponse.class);
        if (response != null) {
            response.getValue().forEach(currency -> 
                currencyCache.put(currency.getSimbolo(), currency.getNomeFormatado())
            );
        }
    }

    public ExchangeRateApiResponse getExchangeRate(String currencyCode, LocalDate startDate, LocalDate endDate) {
      // Convert LocalDate to String in the required format (e.g., "MM-dd-yyyy")
      String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
      String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
  
      String url = String.format(EXCHANGE_RATE_URL_TEMPLATE, currencyCode, formattedStartDate, formattedEndDate);
      return restTemplate.getForObject(url, ExchangeRateApiResponse.class);
  }  
}
