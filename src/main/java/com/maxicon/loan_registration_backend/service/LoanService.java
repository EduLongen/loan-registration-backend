package com.maxicon.loan_registration_backend.service;

import com.maxicon.loan_registration_backend.entity.Client;
import com.maxicon.loan_registration_backend.entity.Loan;
import com.maxicon.loan_registration_backend.repository.ClientRepository;
import com.maxicon.loan_registration_backend.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CurrencyService currencyService;
    
    public Loan registerLoan(Loan loan, BigDecimal interestRate) {
        // Ensure the interest rate is valid
        if (interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Interest rate must be greater than 0");
        }
    
        BigDecimal principal = loan.getAmount();
        int compoundsPerYear = 12;
    
        // Calculate the number of months between loanDate and dueDate
        long months = ChronoUnit.MONTHS.between(loan.getLoanDate(), loan.getDueDate());
        if (months <= 0) {
            throw new IllegalArgumentException("Due date must be after loan date");
        }
    
        // Set the calculated months in the Loan entity
        loan.setMonths((int) months);  
        
        // Calculate the total amount with compound interest
        BigDecimal totalAmount = calculateCompoundInterest(
            principal,
            interestRate,
            months,
            compoundsPerYear
        );
    
        loan.setTotalAmount(totalAmount);
        loan.setInterestRate(interestRate);
    
        // Fetch the exchange rate to BRL from the CurrencyService
        BigDecimal exchangeRateToBRL = currencyService.getExchangeRateToBRL(loan.getCurrency());
        loan.setExchangeRateToBRL(exchangeRateToBRL);
    
        // Calculate the total amount in BRL and store it
        BigDecimal totalInBRL = totalAmount.multiply(exchangeRateToBRL).setScale(2, RoundingMode.HALF_UP);
        loan.setTotalInBRL(totalInBRL);
    
        return loanRepository.save(loan);
    }       

    // Correct method to calculate compound interest using months
    private BigDecimal calculateCompoundInterest(BigDecimal principal, BigDecimal annualInterestRate, long months, int compoundsPerYear) {
        // Convert months to years (e.g., 13 months = 13 / 12 years)
        BigDecimal years = BigDecimal.valueOf(months).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // Calculate the interest rate per compounding period
        BigDecimal interestPerPeriod = annualInterestRate.divide(BigDecimal.valueOf(compoundsPerYear), 10, RoundingMode.HALF_UP);

        // Calculate the number of compounding periods
        BigDecimal numberOfPeriods = years.multiply(BigDecimal.valueOf(compoundsPerYear));

        // Apply the compound interest formula: A = P(1 + r/n)^(nt)
        BigDecimal totalAmount = principal.multiply(BigDecimal.ONE.add(interestPerPeriod).pow(numberOfPeriods.intValue())).setScale(2, RoundingMode.HALF_UP);

        return totalAmount;
    }
    
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    public Loan updateLoan(Long id, Loan loanDetails) {
        Optional<Loan> loanOptional = loanRepository.findById(id);
        if (loanOptional.isPresent()) {
            Loan existingLoan = loanOptional.get();
            existingLoan.setAmount(loanDetails.getAmount());
            existingLoan.setCurrency(loanDetails.getCurrency());
            existingLoan.setLoanDate(loanDetails.getLoanDate());
            existingLoan.setDueDate(loanDetails.getDueDate());
            existingLoan.setInterestRate(loanDetails.getInterestRate());
            return loanRepository.save(existingLoan);
        }
        return null;
    }

    public List<Loan> getLoansByClientCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with CPF: " + cpf));
        return loanRepository.findByClient(client);
    }
}

