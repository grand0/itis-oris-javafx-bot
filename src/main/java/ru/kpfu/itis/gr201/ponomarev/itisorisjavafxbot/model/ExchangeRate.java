package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.model;

import java.util.Objects;

public class ExchangeRate {
    private String baseCurrency;
    private String currency;
    private double value;

    public ExchangeRate(String baseCurrency, String currency, double value) {
        this.baseCurrency = baseCurrency;
        this.currency = currency;
        this.value = value;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Double.compare(getValue(), that.getValue()) == 0 && Objects.equals(getBaseCurrency(), that.getBaseCurrency()) && Objects.equals(getCurrency(), that.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBaseCurrency(), getCurrency(), getValue());
    }

    @Override
    public String toString() {
        return "1 " + baseCurrency + " = " + value + " " + currency;
    }
}
