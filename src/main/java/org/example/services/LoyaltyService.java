package org.example.services;

import org.example.data.ClientDao;
import org.example.model.Client;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyService {

    private final ClientDao clientDao;

    public LoyaltyService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void addClient(String name, boolean hasLoyaltyCard){
        Client client = new Client();
        client.setClientName(name);
        client.setHasLoyaltyCard(hasLoyaltyCard);
        clientDao.save(client);
    }
}
