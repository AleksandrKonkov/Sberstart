package bankEntities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static db.DBConnector.con;
import static db.DBConnector.rs;


@JsonAutoDetect
public class Client {
    private final long id;
    private final String login;

    public Client(long id, String login) {
        this.id = id;
        this.login = login;
    }
    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }


    public static Client getClient(String login) {
        try {
            Client client = null;

            PreparedStatement statement = con.prepareStatement("SELECT * FROM CLIENT WHERE LOGIN = ?");
            statement.setString(1, login);
            rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                client = new Client(id, login);
            }
            return client;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM ACCOUNT WHERE CLIENT_ID = ?");
            statement.setLong(1, this.id);
            rs = statement.executeQuery();
            while (rs.next()) {
                int accountId = rs.getInt("ID");
                String accountNumber = rs.getString("NUMBER");
                BigDecimal balance = rs.getBigDecimal("BALANCE");
                accounts.add(new Account(accountId, accountNumber, balance));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return getLogin().equals(client.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }
}