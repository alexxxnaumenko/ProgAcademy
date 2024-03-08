import exception.BankException;
import model.Currency;
import service.BankManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Runner {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpahw2");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        BankManager bankManager = new BankManager(entityManager);

        try {
            bankManager.fillExchangeRateTable();
            bankManager.fillUserAndAccountTables();

            // task 1 : put money
            bankManager.putMoney(4, 1000.0, Currency.USD);
            bankManager.putMoney(4, 100.0, Currency.USD);

            // task 2 : transfer money from userId 4 to userId 5 - $500

            bankManager.transferMoney(4, 5, 500.0, Currency.USD);

            // task 3 : convert currency : 38.5 -> $1

            bankManager.convertCurrency(4, 38.5, Currency.UAH, Currency.USD);

            // task 4 : convert to UAH for userId = 4
            bankManager.convertAccountsToUah(4);

        } catch (BankException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }


}
