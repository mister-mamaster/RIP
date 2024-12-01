package org.example.Lab2.DB;

import java.sql.ResultSet;

public interface BDExecuter {

    public ResultSet executeQuery(String query);

    public int executeUpdate(String query);
}
