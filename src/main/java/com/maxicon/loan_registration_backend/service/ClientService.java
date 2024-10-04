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
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoanRepository loanRepository;

    public Client registerClient(Client client) {
        Optional<Client> existingClient = clientRepository.findByCpf(client.getCpf());

        if (existingClient.isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este cpf: " + client.getCpf());
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

    public void deleteClient(Long id) {
        // First, find the client by id
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));

        // Now, check if the client has any associated loans
        List<Loan> loans = loanRepository.findByClient(client);

        if (!loans.isEmpty()) {
            throw new IllegalArgumentException("Cliente possui empréstimos ativos e não pode ser excluído");
        }

        // If no loans are found, proceed to delete the client
        clientRepository.deleteById(id);
    }
    
}
