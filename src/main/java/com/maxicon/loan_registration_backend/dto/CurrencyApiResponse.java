package com.maxicon.loan_registration_backend.dto;

import java.util.List;

public class CurrencyApiResponse {

    private List<Currency> value;

    public List<Currency> getValue() {
        return value;
    }

    public void setValue(List<Currency> value) {
        this.value = value;
    }

    public static class Currency {
        private String simbolo;
        private String nomeFormatado;

        public String getSimbolo() {
            return simbolo;
        }

        public void setSimbolo(String simbolo) {
            this.simbolo = simbolo;
        }

        public String getNomeFormatado() {
            return nomeFormatado;
        }

        public void setNomeFormatado(String nomeFormatado) {
            this.nomeFormatado = nomeFormatado;
        }
    }
}
