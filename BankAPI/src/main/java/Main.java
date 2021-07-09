

import java.io.IOException;
import java.sql.SQLException;

import static controller.Server.startServer;
import static db.DBConnector.*;


public class Main
{

    public static void main(String[] args) throws IOException, SQLException {
        createConnection();
        dbInit();
        startServer();

    }
}




