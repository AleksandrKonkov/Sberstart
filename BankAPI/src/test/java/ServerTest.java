import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import static controller.ServerHttpBank.startServer;
import static controller.ServerHttpBank.stopServer;
import static db.DBConnector.createConnection;
import static db.DBConnector.dbInit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerTest {
    private String address;
    private String requestBody;
    private HttpURLConnection connection;
    URL url;

    @BeforeAll
    public static void startServerAndDB() throws IOException, SQLException {

        createConnection();
        dbInit();
        startServer();
    }

    @AfterAll
    public static void stopServerAndDB() {
        stopServer();
    }

    @Test
    @Order(1)
    void checkBalance_GET_responce() throws IOException {
        address = "http://localhost:8030/clients/john/cards/1";
        url = new URL(address);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.disconnect();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String actual = in.readLine();

        String expected = "{\"balance\":10.10}";

        Assertions.assertEquals(expected, actual);
    }


}
