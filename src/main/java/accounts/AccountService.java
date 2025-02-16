package accounts;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class AccountService {
    private static final String DB_URL = "jdbc:h2:./h2db";
    private static final String DB_USER = "test";
    private static final String DB_PASS = "test";

    public AccountService(){
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String createTableQuery = """
                    CREATE TABLE IF NOT EXISTS users (
                    id BIGINT AUTO_INCREMENT,
                    login VARCHAR(256),
                    password VARCHAR(256),
                    PRIMARY KEY(id)
                    );
                    """;
            connection.createStatement().execute(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void signUp (String login, String pass){
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)){
            String insertQuery = "INSERT INTO users (login, password) VALUES (?,?)";
            PreparedStatement prepstmt = connection.prepareStatement(insertQuery);
            prepstmt.setString(1, login);
            prepstmt.setString(2, pass);
            prepstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean signIn (String login, String pass){
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)){
            String selectQuery = "SELECT * FROM users WHERE login = ? AND password = ?";
            PreparedStatement prepstmt = connection.prepareStatement(selectQuery);
            prepstmt.setString(1, login);
            prepstmt.setString(2, pass);
            ResultSet resultSet = prepstmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
