package com.maxicon.loan_registration_backend.repository;

import com.maxicon.loan_registration_backend.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    // Optionally, you can add a method to find by currency symbol
    Optional<Currency> findBySymbol(String symbol);
}
