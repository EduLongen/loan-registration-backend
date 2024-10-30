package com.maxicon.loan_registration_backend.repository;

import com.maxicon.loan_registration_backend.entity.ExchangeRate;
import com.maxicon.loan_registration_backend.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    List<ExchangeRate> findByCurrencyAndDate(Currency currency, LocalDate date);
    List<ExchangeRate> findByCurrency(Currency currency);
}
