package org.example.Lab2.DB;

import java.sql.Connection;
import java.sql.SQLException;

public interface BDConnector {

    public Connection createConnect(String login, String password) throws SQLException;

    public void disconnect() throws SQLException;
}
