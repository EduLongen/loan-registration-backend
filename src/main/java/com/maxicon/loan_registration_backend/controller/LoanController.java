package com.maxicon.loan_registration_backend.controller;

import com.maxicon.loan_registration_backend.dto.LoanRequest;
import com.maxicon.loan_registration_backend.entity.Client;
import com.maxicon.loan_registration_backend.entity.Loan;
import com.maxicon.loan_registration_backend.service.ClientService;
import com.maxicon.loan_registration_backend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:4200")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody LoanRequest loanRequest) {
        Client client = clientService.findByCpf(loanRequest.getCpf())
                .orElseThrow(() -> new IllegalArgumentException("Não foi possível encontrar o cliente com o cpf: " + loanRequest.getCpf()));

        Loan loan = new Loan();
        loan.setClient(client);
        loan.setAmount(BigDecimal.valueOf(loanRequest.getAmount()));
        loan.setCurrency(loanRequest.getCurrency());
        loan.setLoanDate(loanRequest.getLoanDate());
        loan.setDueDate(loanRequest.getDueDate());

        BigDecimal interestRate = BigDecimal.valueOf(loanRequest.getInterestRate());

        Loan savedLoan = loanService.registerLoan(loan, interestRate);

        return ResponseEntity.ok(savedLoan);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/client/{cpf}")  // Keep this endpoint for fetching loans by client CPF
    public ResponseEntity<List<Loan>> getLoansByClientCpf(@PathVariable String cpf) {
        List<Loan> loans = loanService.getLoansByClientCpf(cpf);
        if (loans.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(loans);
    }
}
