package com.maxicon.loan_registration_backend.dto;

import java.util.List;

public class ExchangeRateApiResponse {

    private List<ExchangeRate> value;

    public List<ExchangeRate> getValue() {
        return value;
    }

    public void setValue(List<ExchangeRate> value) {
        this.value = value;
    }

    public static class ExchangeRate {
        private String cotacaoCompra;
        private String cotacaoVenda;
        private String dataHoraCotacao; 

        public String getCotacaoCompra() {
            return cotacaoCompra;
        }

        public void setCotacaoCompra(String cotacaoCompra) {
            this.cotacaoCompra = cotacaoCompra;
        }

        public String getCotacaoVenda() {
            return cotacaoVenda;
        }

        public void setCotacaoVenda(String cotacaoVenda) {
            this.cotacaoVenda = cotacaoVenda;
        }

        public String getDataHoraCotacao() {
            return dataHoraCotacao;
        }

        public void setDataHoraCotacao(String dataHoraCotacao) {
            this.dataHoraCotacao = dataHoraCotacao;
        }
    }
}
