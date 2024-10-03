package com.maxicon.loan_registration_backend.dto;

import java.time.LocalDate;

public class LoanRequest {
    private String cpf;
    private double amount;
    private String currency;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private double interestRate;
    private int compoundsPerYear;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getCompoundsPerYear() {
        return compoundsPerYear;
    }

    public void setCompoundsPerYear(int compoundsPerYear) {
        this.compoundsPerYear = compoundsPerYear;
    }
}
