package model;

import enums.TransactionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private LocalDateTime transactionTime;

    @Column(name = "Type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column
    private String message;

    public TransactionHistory() {
    }

    public TransactionHistory(User user, TransactionType transactionType, Double amount, Currency currency) {
        this.user = user;
        this.transactionType = transactionType;
        this.transactionTime = LocalDateTime.now();
        this.message = String.format(transactionType.getMessage(), amount, currency);
    }

    public TransactionHistory(User user, User oppositeUser, TransactionType transactionType, Double amount, Currency currency) {
        this.user = user;
        this.transactionType = transactionType;
        this.transactionTime = LocalDateTime.now();
        this.message = String.format(transactionType.getMessage(), amount, currency, oppositeUser.getId());
    }

    public TransactionHistory(User user, TransactionType transactionType, Double amountFrom, Currency currencyFrom, Double convertedAmount, Currency currencyTo) {
        this.user = user;
        this.transactionType = transactionType;
        this.transactionTime = LocalDateTime.now();
        this.message = String.format(transactionType.getMessage(), amountFrom, currencyFrom, convertedAmount, currencyTo);
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }


}
