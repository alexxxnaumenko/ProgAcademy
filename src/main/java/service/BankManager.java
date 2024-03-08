package service;

import exception.BankException;
import model.Account;
import model.Currency;
import model.ExchangeRate;
import model.TransactionHistory;
import enums.TransactionType;
import model.User;
import enums.ExchangeRateConstants;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;

import static enums.ExchangeRateConstants.EUR_UAH;
import static enums.ExchangeRateConstants.UAH_USD;
import static enums.ExchangeRateConstants.USD_UAH;


public class BankManager implements BankManagerInterface {

    private final EntityManager entityManager;

    public BankManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void putMoney(Integer userId, Double amount, Currency currency) throws BankException {
        User user = validateAndGetUser(userId);
        Account account = validateAndGetAccount(currency, user);

        account.addAmount(amount);

        TransactionHistory th = new TransactionHistory(user, TransactionType.DEPOSIT, amount, currency);
        transactionWrapper(() -> {
            entityManager.persist(user);
            entityManager.persist(th);
        });
    }

    @Override
    public void transferMoney(Integer userIdFrom, Integer userIdTo, Double amount, Currency currency) throws BankException {

        if (userIdFrom.equals(userIdTo)) {
            throw new IllegalArgumentException("userIdFrom is equal to userIdTo");
        }

        User userFrom = validateAndGetUser(userIdFrom);
        User userTo = validateAndGetUser(userIdTo);
        Account accountFrom = validateAndGetAccount(currency, userFrom);
        Account accountTo = validateAndGetAccount(currency, userTo);

        if (accountFrom.getAmount() < amount) {
            throw new BankException("Not enough money to transfer");
        }

        accountFrom.setAmount(accountFrom.getAmount() - amount);
        accountTo.setAmount(accountTo.getAmount() + amount);

        TransactionHistory thFrom = new TransactionHistory(userFrom, userTo, TransactionType.TRANSFER, amount, currency);
        TransactionHistory thTo = new TransactionHistory(userTo, userFrom, TransactionType.RECEIVING_AFTER_TRANSFER, amount, currency);

        transactionWrapper(() -> {
            entityManager.persist(userFrom);
            entityManager.persist(userTo);
            entityManager.persist(thFrom);
            entityManager.persist(thTo);
        });
    }

    @Override
    public void convertCurrency(Integer userId, Double amount, Currency currencyFrom, Currency currencyTo) throws BankException {
        User user = validateAndGetUser(userId);
        Account accountFrom = validateAndGetAccount(currencyFrom, user);
        Account accountTo = validateAndGetAccount(currencyTo, user);

        if (accountFrom.getAmount() < amount) {
            throw new BankException("Not enough money to convert");
        }

        ExchangeRateConstants rate = Arrays.stream(ExchangeRateConstants.values())
                .filter((v -> v.name().equals(currencyFrom + "_" + currencyTo)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("CurrencyFrom or CurrencyTo is not correct"));

        ExchangeRate exchangeRate = entityManager.find(ExchangeRate.class, rate.getFullName());

        Double convertedAmount = amount / exchangeRate.getRate();

        accountTo.setAmount(accountTo.getAmount() + convertedAmount);
        accountFrom.setAmount(accountFrom.getAmount() - amount);

        TransactionHistory th = new TransactionHistory(user, TransactionType.CONVERT, amount, currencyFrom, convertedAmount, currencyTo);

        transactionWrapper(() -> {
            entityManager.persist(user);
            entityManager.persist(th);
        });
    }

    @Override
    public Double convertAccountsToUah(Integer userId) throws BankException {
        User user = validateAndGetUser(userId);

        Set<Account> accounts = user.getAccounts();

        TypedQuery<ExchangeRate> getAllRates = entityManager.createNamedQuery("getAllRates", ExchangeRate.class);

        return accounts.stream()
                .mapToDouble(a -> Currency.UAH == a.getCurrency()
                        ? a.getAmount()
                        : a.getAmount() * getAllRates.getResultStream()
                        .filter(e -> e.getName().equals(a.getCurrency().name().toLowerCase() + "/uah"))
                        .findFirst()
                        .map(ExchangeRate::getRate)
                        .orElseThrow(IllegalArgumentException::new))
                .parallel()
                .reduce(0, Double::sum);
    }

    private Account validateAndGetAccount(Currency currency, User userFrom) throws BankException {
        return userFrom.getAccounts().stream()
                .filter(a -> a.getCurrency().equals(currency))
                .findFirst()
                .orElseThrow(() -> new BankException("Account not found for user .." + userFrom.getId()));
    }

    private User validateAndGetUser(Integer userId) throws BankException {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new BankException("User not found");
        }
        return user;
    }

    private void transactionWrapper(MyEmptyFunctionalInterface myEmptyFunctionalInterface) {
        try {
            entityManager.getTransaction().begin();
            myEmptyFunctionalInterface.apply();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e.getCause());
        }
    }

    private <T> T transactionWrapperWithResult(Supplier<T> supplier) {
        T result;
        try {
            entityManager.getTransaction().begin();
            result = supplier.get();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e.getCause());
        }
        return result;
    }


    public void fillExchangeRateTable() {
        ExchangeRate uahUsd = new ExchangeRate(UAH_USD.getFullName(), 38.5);
        ExchangeRate usdUah = new ExchangeRate(USD_UAH.getFullName(), 38.23);
        ExchangeRate eurUah = new ExchangeRate(EUR_UAH.getFullName(), 41.84);
        transactionWrapper(() -> {
            entityManager.persist(uahUsd);
            entityManager.persist(usdUah);
            entityManager.persist(eurUah);
        });
    }

    public void fillUserAndAccountTables() {
        User petro = new User("Petro");
        User vasyl = new User("Vasyl");
        petro.addAccount(new Account(Currency.UAH, 100.0));
        petro.addAccount(new Account(Currency.USD, 0.0));
        vasyl.addAccount(new Account(Currency.USD, 100.0));
        transactionWrapper(() -> {
            entityManager.persist(petro);
            entityManager.persist(vasyl);
        });
    }

    private interface MyEmptyFunctionalInterface {
        void apply();
    }
}
