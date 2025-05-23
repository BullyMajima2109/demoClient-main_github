package com.example.democlient.service.impl;

import com.example.democlient.model.Client;
import com.example.democlient.repository.ClientRepository;
import com.example.democlient.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> listAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client listById(int idClient) {
        return clientRepository.findById(idClient).get();
    }

    @Override
    public Client create(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(int idClient) {
        clientRepository.deleteById(idClient);
    }
}
