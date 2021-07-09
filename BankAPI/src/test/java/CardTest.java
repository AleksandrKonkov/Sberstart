import bankEntities.Card;
import db.DBConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.SqlLoader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static db.DBConnector.closeConnection;
import static db.DBConnector.createConnection;

public class CardTest {
    @BeforeAll
    public static void beforeAll() throws SQLException {
        createConnection();
        List<String> listFileData = SqlLoader.load("src/main/resourses/Scr.sql");
        String sql = SqlLoader.listToString(listFileData);
        Statement statement = DBConnector.con.createStatement();
        statement.execute(sql);
        statement.executeUpdate("INSERT INTO CLIENT (ID, LOGIN) VALUES ( 1, 'Murka' )");
        statement.executeUpdate("INSERT INTO ACCOUNT (NUMBER, BALANCE, CLIENT_ID) VALUES ( '0000', 100.10, 1 );");
        statement.executeUpdate("INSERT INTO CARD (NUMBER, ACCOUNT_ID) VALUES ( '1000000000000000'," +
                "(SELECT ID FROM ACCOUNT WHERE ACCOUNT.NUMBER = '0000'));");
    }

    @AfterAll
    public static void afterAll() throws SQLException {
        closeConnection();
    }

    @Test
    void getBalance() {
        Card card = new Card(1, "1000000000000000");
        BigDecimal balanceActual = card.getBalance();
        BigDecimal balanceExpected = new BigDecimal(100.1).setScale(2, RoundingMode.HALF_UP);

        Assertions.assertEquals(balanceExpected, balanceActual);

    }

    @Test
    void setCardBalance() {
        Card card = new Card(1,"1000000000000000");

        card.setCardBalance(new BigDecimal("50.1"));
        BigDecimal balanceActual = card.getBalance();
        BigDecimal balanceExpected = new BigDecimal("50.10").setScale(2, RoundingMode.HALF_UP);

        Assertions.assertEquals(balanceExpected, balanceActual);
    }

}
