package com.example.democlient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.democlient.model.Client;
import com.example.democlient.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<?> listAll() {
        List<Client> clients = clientService.listAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<?> listById(@PathVariable int idClient) {
        Client client = clientService.listById(idClient);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Client client) {
        Client clientCreate = clientService.create(client);
        return ResponseEntity.ok(clientCreate);
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<?> update(@PathVariable int idClient, @RequestBody Client client) {
        client.setIdClient(idClient);
        Client clientUpdate = clientService.update(client);
        return ResponseEntity.ok(clientUpdate);
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<?> deleteById(@PathVariable int idClient) {
        clientService.deleteById(idClient);
        return ResponseEntity.ok(null);
    }

}
