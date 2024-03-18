package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class NbuCurrency {

    @Id
    @GeneratedValue
    private Integer id;
    private LocalDate exchangedate;
    private String r030;
    private String cc;
    private String txt;
    private String enname;
    private Double rate;
    private Integer units;
    @Column(name = "rateunit")
    private Double rate_per_unit;
    @Column(name = "curr_group")
    private Integer group;
    private LocalDate calcdate;

    public NbuCurrency() {
    }

    public NbuCurrency(LocalDate exchangedate, String r030, String cc, String txt, String enname, Double rate, Integer units, Double rate_per_unit, Integer group, LocalDate calcdate) {
        this.exchangedate = exchangedate;
        this.r030 = r030;
        this.cc = cc;
        this.txt = txt;
        this.enname = enname;
        this.rate = rate;
        this.units = units;
        this.rate_per_unit = rate_per_unit;
        this.group = group;
        this.calcdate = calcdate;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getExchangedate() {
        return exchangedate;
    }

    public void setExchangedate(LocalDate exchangedate) {
        this.exchangedate = exchangedate;
    }

    public String getR030() {
        return r030;
    }

    public void setR030(String r030) {
        this.r030 = r030;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Double getRate_per_unit() {
        return rate_per_unit;
    }

    public void setRate_per_unit(Double rate_per_unit) {
        this.rate_per_unit = rate_per_unit;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public LocalDate getCalcdate() {
        return calcdate;
    }

    public void setCalcdate(LocalDate calcdate) {
        this.calcdate = calcdate;
    }

    @Override
    public String toString() {
        return "NbuCurrency{" +
                "id=" + id +
                ", exchangedate=" + exchangedate +
                ", r030=" + r030 +
                ", cc='" + cc + '\'' +
                ", txt='" + txt + '\'' +
                ", enname='" + enname + '\'' +
                ", rate=" + rate +
                ", units=" + units +
                ", rate_per_unit=" + rate_per_unit +
                ", group=" + group +
                ", calcdate=" + calcdate +
                '}';
    }
}
