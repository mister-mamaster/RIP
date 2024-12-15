package org.example.Lab2.DB;

import java.sql.ResultSet;

public interface DBExecuter {

    public ResultSet executeQuery(String query);

    public int executeUpdate(String query);
}
