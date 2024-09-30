package com.maxicon.loan_registration_backend.controller;

import com.maxicon.loan_registration_backend.dto.LoanRequest;
import com.maxicon.loan_registration_backend.entity.Client;
import com.maxicon.loan_registration_backend.entity.Loan;
import com.maxicon.loan_registration_backend.service.ClientService;
import com.maxicon.loan_registration_backend.service.LoanService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @PostMapping  // Handles POST requests to /api/loans
    public ResponseEntity<Loan> createLoan(@RequestBody LoanRequest loanRequest) {
        // Retrieve the client by CPF
        Client client = clientService.findByCpf(loanRequest.getCpf())
                .orElseThrow(() -> new IllegalArgumentException("Client not found with CPF: " + loanRequest.getCpf()));

        // Create a new loan and set its properties
        Loan loan = new Loan();
        loan.setClient(client);
        loan.setAmount(loanRequest.getAmount());
        loan.setCurrency(loanRequest.getCurrency());
        loan.setLoanDate(loanRequest.getLoanDate());
        loan.setDueDate(loanRequest.getDueDate());

        // Save the loan
        Loan savedLoan = loanService.registerLoan(loan);

        return ResponseEntity.ok(savedLoan);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/client/{cpf}")
    public ResponseEntity<List<Loan>> getLoansByClientCpf(@PathVariable String cpf) {
        List<Loan> loans = loanService.getLoansByClientCpf(cpf);
        if (loans.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(loans);
    }
}
