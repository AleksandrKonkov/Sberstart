package service;

import bankEntities.Account;
import bankEntities.Card;
import bankEntities.Client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static bankEntities.Card.getCard;
import static bankEntities.Card.getLastCardNumber;
import static bankEntities.Client.getClient;
import static service.ObjToJsonConverter.*;

public class EntitiesService {


    public String addMoneyToCard(String sumString, long cardId, boolean fromJson) {
        BigDecimal sum;
        if (fromJson)
            sum = new BigDecimal(covertJsonToMap(sumString).get("sum"));
        else
            sum = new BigDecimal(sumString);
        Card card = getCard(cardId);
        if (card == null)
            return null;
        else {
            BigDecimal prev = card.getBalance();
            card.setCardBalance(prev.add(sum));
            return convertCardBalanceToJson(Collections.singletonMap("balance", card.getBalance()));
        }
    }



    public String getBalance(long cardId) {
        Card card = getCard(cardId);
        if (card == null)
            return null;
        else {
            BigDecimal balance = card.getBalance().setScale(2, RoundingMode.DOWN);
            return convertCardBalanceToJson(Collections.singletonMap("balance", balance));
        }
    }



    public String getAllCards(String clientLogin) {
        Client client = getClient(clientLogin);
        List<Object> cardsList = new ArrayList<>();

        client.getAccounts().forEach(account -> cardsList.addAll(account.getCards()));
        return convertListToJsonString(cardsList);

    }


    public String createNewCard(String accountIdString){
        long accountId = Long.parseLong(covertJsonToMap(accountIdString).get("id"));
        if (Account.ifExists(accountId)) {
            String lastCardNumber = getLastCardNumber();
            Long number = Long.parseLong(lastCardNumber);
            number++;
            String currentNum = String.format("%016d", number);
            Card card = new Card(0, currentNum);
            Card.addCard(card, accountId);
            return convertCardToJson(getCard(currentNum));
        }
        else return null;
   }}
