import db.DBConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.EntitiesService;
import service.SqlLoader;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static db.DBConnector.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EntitiesServiceTest {

    @BeforeAll
    static void beforeAll() throws SQLException {
        createConnection();
        List<String> listFileData = SqlLoader.load("src/main/resourses/Scr.sql");
        String sql = SqlLoader.listToString(listFileData);
        Statement statement = DBConnector.con.createStatement();
        statement.execute(sql);
        statement.executeUpdate("INSERT INTO CLIENT (ID, LOGIN) VALUES (10, 'MURZIK' ), (11, 'MURKA'), (12, 'PUSHOK');");
        statement.executeUpdate("INSERT INTO ACCOUNT (ID,NUMBER, BALANCE, CLIENT_ID) VALUES ( 10,'0000', 100, 10), ( 11,'0001', 200, 11);");
        statement.executeUpdate("INSERT INTO CARD (ID,NUMBER, ACCOUNT_ID) VALUES ( 10,'0000111100001111', 10), ( 11,'1111111100001111', 11);");

    }

    @AfterAll
    static void afterAll() {
        closeConnection();
    }

    @Test
    void addMoneyToCard() {
        String jsonString = "{\"sum\" : 50}";
        EntitiesService service = new EntitiesService();
        String actualResponse = service.addMoneyToCard(jsonString, 10, true);
        String expectedResponse = "{\"balance\":150.00}";
        String negativeResponse = service.addMoneyToCard(jsonString, 15, true);

        Assertions.assertEquals(expectedResponse, actualResponse);
        assertNull(negativeResponse);
    }

    @Test
    void getBalance() {
        EntitiesService service = new EntitiesService();
        String actualResponse = service.getBalance(11);
        String expectedResponse = "{\"balance\":200.00}";
        String negativeResponse = service.getBalance(15);

        Assertions.assertEquals(expectedResponse, actualResponse);
        assertNull(negativeResponse);

    }


    @Test
    void getAllCards() {
        EntitiesService service = new EntitiesService();
        String actualResponse = service.getAllCards("MURKA");
        String expectedResponse = "[{\"id\":11,\"number\":\"1111111100001111\"}]";
        String negativeResponse = service.getAllCards("PUSHOK");

        Assertions.assertEquals(expectedResponse, actualResponse);
        assertNull(negativeResponse);
    }


    @Test
    void createNewCard() throws SQLException {

        EntitiesService service = new EntitiesService();
        String actualResponse = service.createNewCard("{\"id\" : 10}");
        String expectedResponse = "{\"id\":12,\"number\":\"1111111100001112\"}";
        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}
