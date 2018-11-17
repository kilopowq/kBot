package com.kilopo.exchange.rate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {

    private String date;
    private String bank;
    private String baseCurrencyLit;
    private List<ExchangeRate> exchangeRate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    public void setBaseCurrencyLit(String baseCurrencyLit) {
        this.baseCurrencyLit = baseCurrencyLit;
    }

    public List<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(List<ExchangeRate> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
