package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.model;

import java.util.Objects;

public class Weather {
    private String city;
    private double temp;
    private double humidity;
    private String description;

    public Weather(String city, double temp, double humidity, String description) {
        this.city = city;
        this.temp = temp;
        this.humidity = humidity;
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Double.compare(getTemp(), weather.getTemp()) == 0 && Double.compare(getHumidity(), weather.getHumidity()) == 0 && Objects.equals(getCity(), weather.getCity()) && Objects.equals(getDescription(), weather.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getTemp(), getHumidity(), getDescription());
    }

    @Override
    public String toString() {
        return "Weather in " + city + ": " + description + ", temperature " + temp + "°C, humidity " + humidity + "%.";
    }
}
