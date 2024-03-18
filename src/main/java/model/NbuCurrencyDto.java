package model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class NbuCurrencyDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate exchangedate;
    private String r030;
    private String cc;
    private String txt;
    private String enname;
    private Double rate;
    private Integer units;
    private Double rate_per_unit;
    private Integer group;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate calcdate;

    public void setExchangedate(LocalDate exchangedate) {
        this.exchangedate = exchangedate;
    }

    public void setR030(String r030) {
        this.r030 = r030;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public void setRate_per_unit(Double rate_per_unit) {
        this.rate_per_unit = rate_per_unit;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public void setCalcdate(LocalDate calcdate) {
        this.calcdate = calcdate;
    }


    public LocalDate getExchangedate() {
        return exchangedate;
    }

    public String getR030() {
        return r030;
    }

    public String getCc() {
        return cc;
    }

    public String getTxt() {
        return txt;
    }

    public String getEnname() {
        return enname;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getUnits() {
        return units;
    }

    public Double getRate_per_unit() {
        return rate_per_unit;
    }

    public Integer getGroup() {
        return group;
    }

    public LocalDate getCalcdate() {
        return calcdate;
    }

    public NbuCurrencyDto() {
    }

    @Override
    public String toString() {
        return "NbuCurrencyDto{" +
                "exchangedate=" + exchangedate +
                ", r030='" + r030 + '\'' +
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
