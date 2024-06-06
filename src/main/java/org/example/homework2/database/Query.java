package org.example.homework2.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

    private Query() {
    }

    private static Connection connection;


    public static ResultSet getData(String sql) throws SQLException {
        PreparedStatement statement = getPreparedStatement(sql);
        return statement.executeQuery();
    }
    public static Integer executeQuery (PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }
    public static ResultSet getResultSet(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }
    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        connection = DataSource.getConnection();
        return connection.prepareStatement(sql);
    }
    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
