package com.maxicon.loan_registration_backend.controller;

import com.maxicon.loan_registration_backend.entity.Client;
import com.maxicon.loan_registration_backend.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<?> registerClient(@RequestBody Client client) {
        try {
            Client registeredClient = clientService.registerClient(client);
            return new ResponseEntity<>(registeredClient, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }       

    @GetMapping("/{cpf}")
    public ResponseEntity<Client> getClientByCpf(@PathVariable String cpf) {
        Optional<Client> client = clientService.findByCpf(cpf);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            // Perform delete operation
            clientService.deleteClient(id);
            // Return success response if deletion is successful
            return ResponseEntity.ok(Collections.singletonMap("message", "Client deleted successfully"));
        } catch (IllegalArgumentException e) {
            // Return a 400 Bad Request with the error message if the client has active loans
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Collections.singletonMap("error", e.getMessage()));

        }
    }    
    
}
