package com.maxicon.loan_registration_backend.repository;

import com.maxicon.loan_registration_backend.entity.Client;
import com.maxicon.loan_registration_backend.entity.Loan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByClient(Client client);
}
