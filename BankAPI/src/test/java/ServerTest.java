import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import static controller.ServerHttpBank.startServer;
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
        LaunchDB.disconnect();
        Server.stop();
    }

    @Test
    @Order
    void getAllCards_GET_TestShouldReturnString() throws IOException {
        address = "http://localhost:8000/getAllCards";
        url = new URL(address);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.disconnect();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String actual = in.readLine();

        String expected = "[{\"id\":1,\"card_number\":\"111000000000\"},{\"id\":2,\"card_number\":\"111000000001\"},{\"id\":3,\"card_number\":\"111000000002\"}]";

        Assertions.assertEquals(expected, actual);
    }


}
