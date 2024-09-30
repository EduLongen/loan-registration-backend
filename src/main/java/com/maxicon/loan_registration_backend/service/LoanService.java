package com.maxicon.loan_registration_backend.service;

import com.maxicon.loan_registration_backend.entity.Client;
import com.maxicon.loan_registration_backend.entity.Loan;
import com.maxicon.loan_registration_backend.repository.ClientRepository;
import com.maxicon.loan_registration_backend.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    // Register a loan - client is already part of the loan entity
    public Loan registerLoan(Loan loan) {
        return loanRepository.save(loan);
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
            return loanRepository.save(existingLoan);
        }
        return null;  // Handle error case when loan not found
    }

    public List<Loan> getLoansByClientCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with CPF: " + cpf));
        return loanRepository.findByClient(client);
    }
}
