package org.example.services;

import com.example.prizes.client.Prize;
import com.example.prizes.client.PrizeClient;
import org.example.data.ClientDao;
import org.example.exceptions.NoPrizeInInventoryException;
import org.example.exceptions.NotEnoughPointsForPrizeException;
import org.example.exceptions.PrizeOutOfStockException;
import org.example.model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Service
public class LoyaltyService {

    private final ClientDao clientDao;
    private final PrizeClient prizeClient;

    public LoyaltyService(ClientDao clientDao, PrizeClient prizeClient) {
        this.clientDao = clientDao;
        this.prizeClient = prizeClient;
    }

    public void addClient(String name, String phoneNumber) {
        Client client = new Client();
        client.setClientName(name);
        client.setPhoneNumber(phoneNumber);
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

    public List<String> checkPrizesForClient(String phoneNumber){

       return prizeClient.getPrizes()
                .stream()
                .filter(prize -> prize.threshold() <= clientDao.getPointsByPhoneNumber(phoneNumber).get())
                .map(Prize::prizeName)
                .toList();

    }

    public String getPrizeForClient(String phoneNumber, String prizeName) {
        int clientPoints = clientDao.getPointsByPhoneNumber(phoneNumber).get();
        List<Prize> prizes = prizeClient.getPrizes();

        Prize prizeFromInventory = prizes
                .stream()
                .filter(prize -> prize.prizeName().equalsIgnoreCase(prizeName))
                .findFirst()
                .orElseThrow(() -> new NoPrizeInInventoryException(prizeName));

        if (prizeFromInventory.threshold() > clientPoints) {
            throw new NotEnoughPointsForPrizeException(prizeFromInventory.prizeName());
        }

        try {
            prizeClient.takePrize(prizeFromInventory.prizeName());
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode() == HttpStatus.CONFLICT) {
                throw new PrizeOutOfStockException(prizeFromInventory.prizeName(), exception);
            }
            throw exception;
        }

        return "Client received " + prizeFromInventory.prizeName();
    }
}
