package enums;

import model.Currency;

import static model.Currency.EUR;
import static model.Currency.UAH;
import static model.Currency.USD;

public enum ExchangeRateConstants {

    UAH_USD("uah/usd", UAH, USD),
    USD_UAH("usd/uah", USD, UAH),
    EUR_UAH("eur/uah", EUR, UAH);

    private final String fullName;
    private final Currency nameFrom;
    private final Currency nameTo;


    ExchangeRateConstants(String fullName, Currency nameFrom, Currency nameTo) {
        this.fullName = fullName;
        this.nameFrom = nameFrom;
        this.nameTo = nameTo;
    }

    public String getFullName() {
        return fullName;
    }

    public Currency getNameFrom() {
        return nameFrom;
    }

    public Currency getNameTo() {
        return nameTo;
    }


}
