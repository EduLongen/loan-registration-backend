package com.maxicon.loan_registration_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol; // 3-letter currency code (e.g., USD, EUR)

    @Column(nullable = false)
    private String name; // Full name of the currency (e.g., US Dollar, Euro)

    // Optional: Add manual getters and setters if Lombok is not working
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
