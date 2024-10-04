package com.maxicon.loan_registration_backend.repository;

import com.maxicon.loan_registration_backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCpf(String cpf);
    Optional<Client> findById(Long id);
}
