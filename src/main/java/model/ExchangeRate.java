package model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "Exchange_rates")
@NamedQuery(name="getAllRates", query="select r from ExchangeRate r")
public class ExchangeRate {

    @Id
    private String name;

    private Double rate;

    public ExchangeRate() {
    }

    public ExchangeRate(String name, Double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                '}';
    }
}
