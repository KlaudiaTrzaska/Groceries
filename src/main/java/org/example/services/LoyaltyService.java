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

    public void addClient(String name) {
        Client client = new Client();
        client.setClientName(name);
        clientDao.save(client);
    }

    public boolean isClientLoyal(String phoneNumber) {

        return clientDao.getClientByPhoneNumber(phoneNumber).isPresent();
    }


    public void addPointsForClient(String phoneNumber, int points) {

        int currentPoints = clientDao.getPointsByPhoneNumber(phoneNumber).get();
        clientDao.updatePointsByPhoneNumber(phoneNumber, currentPoints + points);
    }

    public String checkPointsForClient(String phoneNumber) {
        if (clientDao.getClientByPhoneNumber(phoneNumber).isEmpty()) {
            return "Client does not exist";
        }

        return "Client has " + clientDao.getPointsByPhoneNumber(phoneNumber).get() + " points";
    }
}
