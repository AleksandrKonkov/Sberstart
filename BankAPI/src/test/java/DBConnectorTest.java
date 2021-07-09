import db.DBConnector;
import org.h2.jdbc.JdbcSQLNonTransientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DBConnectorTest {
    @Test
    void createConnection() {
        DBConnector.createConnection();
        assertNotNull(DBConnector.con);
    }

    @Test
    void closeConnection()  {
        DBConnector.createConnection();
        DBConnector.closeConnection();

        Throwable thrown = assertThrows(JdbcSQLNonTransientException.class, () -> {
            Statement statement = DBConnector.con.createStatement();
            statement.executeQuery("SELECT  * FROM CLIENT");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void dbInit() throws SQLException {
        DBConnector.createConnection();

        int actualInit = DBConnector.dbInit();
        int expected = 1;
        Assertions.assertEquals(expected, actualInit);
    }
}


