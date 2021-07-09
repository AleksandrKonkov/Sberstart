package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.EntitiesService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.stream.Collectors;

//POST добавление новой карты
//GET  вывод списка всех карт


public class CardsListHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        EntitiesService service = new EntitiesService();
        String response;
        int responseCode;
        if (exchange.getRequestMethod().equals("POST")) {
            String json= new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            String data = service.createNewCard(json);
            response = data == null ? "Invalid account id" : data;
        }
        else {
            String login = getLoginFromUri(exchange.getRequestURI().toString());
            String data = service.getAllCards(login.toUpperCase(Locale.ROOT));
            response = data == null ? "No cards" : data;
        }
        switch (response){
            case "Invalid account id":
                responseCode = 404;
                break;
            case "No cards":
                responseCode = 204;
                break;
            default:
                responseCode = 200;
                break;
        }
        exchange.sendResponseHeaders(responseCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.flush();
        os.close();
    }

    private String getLoginFromUri(String uri) {
        String[] strings = uri.split("/");
        return strings[2];
    }
}