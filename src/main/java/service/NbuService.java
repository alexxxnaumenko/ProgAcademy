package service;

import model.NbuCurrency;
import model.NbuCurrencyDto;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class NbuService {

    private final EntityManager entityManager;

    private final ModelMapper modelMapper;


    public NbuService(EntityManager entityManager, ModelMapper modelMapper) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    public void saveNbuCurrencyList(List<NbuCurrencyDto> nbuCurrencies) {
        try {
            entityManager.getTransaction().begin();
            nbuCurrencies.forEach(s -> entityManager.persist(modelMapper.map(s, NbuCurrency.class)));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Rollback");
        }
    }

    public NbuCurrencyDto receiveExchangeRateByDate(String date) {
        //should be validation for date
        LocalDate parsedDate = LocalDate.parse(date);
        TypedQuery<NbuCurrency> query = entityManager.createQuery("select n from NbuCurrency n where n.exchangedate = :exchangedate ", NbuCurrency.class);
        query.setParameter("exchangedate", parsedDate);
        return modelMapper.map(query.getSingleResult(), NbuCurrencyDto.class);
    }

    public double receiveAvgValueBetweenRangeOfDates(String firstDate, String secondDate) {
        //should be validation for dates
        LocalDate parsedDate = LocalDate.parse(firstDate);
        LocalDate parsedDate2 = LocalDate.parse(secondDate);
        TypedQuery<Double> query = entityManager.createQuery("select avg(n.rate) from NbuCurrency n where n.exchangedate >= :first and n.exchangedate <= :second", Double.class);
        query.setParameter("first", parsedDate);
        query.setParameter("second", parsedDate2);
        return query.getSingleResult();
    }

    public double receiveMaxRateForPeriod(String firstDate, String secondDate) {
        //should be validation for dates
        LocalDate parsedDate = LocalDate.parse(firstDate);
        LocalDate parsedDate2 = LocalDate.parse(secondDate);
        TypedQuery<Double> query = entityManager.createQuery("select max(n.rate) from NbuCurrency n where n.exchangedate >= :first and n.exchangedate <= :second", Double.class);
        query.setParameter("first", parsedDate);
        query.setParameter("second", parsedDate2);
        return query.getSingleResult();
    }

    public double receiveMinRateForPeriod(String firstDate, String secondDate) {
        //should be validation for dates
        LocalDate parsedDate = LocalDate.parse(firstDate);
        LocalDate parsedDate2 = LocalDate.parse(secondDate);
        TypedQuery<Double> query = entityManager.createQuery("select min(n.rate) from NbuCurrency n where n.exchangedate >= :first and n.exchangedate <= :second", Double.class);
        query.setParameter("first", parsedDate);
        query.setParameter("second", parsedDate2);
        return query.getSingleResult();
    }
}
