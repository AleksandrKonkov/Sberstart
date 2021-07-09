package controller;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import db.DBConnector;


import java.io.IOException;
import java.io.OutputStream;

public class StopApiHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Application closed";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.flush();
        os.close();
        DBConnector.closeConnection();
        System.exit(0);
        ServerHttpBank.stopServer();
    }
}