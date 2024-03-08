package service;

import exception.BankException;
import model.Currency;

public interface BankManagerInterface {

    void putMoney(Integer userId, Double amount, Currency currency) throws BankException ;

    void transferMoney(Integer userIdFrom, Integer userIdTo, Double amount, Currency currency) throws BankException;

    void convertCurrency(Integer userId, Double amount, Currency currencyFrom, Currency currencyTo) throws BankException;;

    Double convertAccountsToUah(Integer userId) throws BankException;
}
