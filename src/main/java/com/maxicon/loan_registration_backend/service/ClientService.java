package com.maxicon.loan_registration_backend.service;

import com.maxicon.loan_registration_backend.entity.Client;
import com.maxicon.loan_registration_backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client registerClient(Client client) {
        Optional<Client> existingClient = clientRepository.findByCpf(client.getCpf());

        if (existingClient.isPresent()) {
            throw new IllegalArgumentException("CPF already exists in the system: " + client.getCpf());
        }

        // Save the new client if CPF does not exist
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> findByCpf(String cpf) {
        return clientRepository.findByCpf(cpf);
    }
}
