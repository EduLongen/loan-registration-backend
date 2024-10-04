package com.maxicon.loan_registration_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private LocalDate loanDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private BigDecimal interestRate;

    @Column(name = "exchange_rate_to_brl", nullable = true)
    private BigDecimal exchangeRateToBRL;

    @Column(name = "total_in_brl", nullable = true)
    private BigDecimal totalInBRL;

    @Column(name = "months", nullable = false)
    private Integer months;

    public BigDecimal getExchangeRateToBRL() {
        return exchangeRateToBRL;
    }

    public void setExchangeRateToBRL(BigDecimal exchangeRateToBRL) {
        this.exchangeRateToBRL = exchangeRateToBRL;
    }

    public BigDecimal getTotalInBRL() {
        return totalInBRL;
    }

    public void setTotalInBRL(BigDecimal totalInBRL) {
        this.totalInBRL = totalInBRL;
    }

    public Integer getMonths() {
        return months;
    }
    
    public void setMonths(Integer months) {
        this.months = months;
    }
    
}
