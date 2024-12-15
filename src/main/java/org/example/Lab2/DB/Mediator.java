package org.example.Lab2.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mediator implements DBConnector, DBExecuter {

    private Connection connection = null;
    @Override
    public Connection createConnect(String login, String password) throws SQLException {
        if (connection != null && !connection.isClosed()) return connection;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/rip",
                    login,
                    password);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void disconnect() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }

    @Override
    public ResultSet executeQuery(String query) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            if(!resultSet.next()) return null;
            resultSet.previous();
            return resultSet;
        } catch (SQLException e) {
            System.out.println("execute failed");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int executeUpdate(String query) {
        try {
            return connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("execute failed");
            e.printStackTrace();
            return 0;
        }
    }
}
