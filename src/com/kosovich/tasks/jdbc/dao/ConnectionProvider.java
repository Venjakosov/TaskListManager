package com.kosovich.tasks.jdbc.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static ConnectionProvider instance;
    private static Connection connection = null;

    private static String url = "jdbc:mysql://localhost:3306/tasks_db";
    private static String username = "root";
    private static String password = "root";

    private ConnectionProvider() throws PersistException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception classNotFoundException) {
            throw new PersistException(classNotFoundException);
        }
    }

    public static ConnectionProvider getInstance() throws PersistException {
        if (instance == null) {
            instance = new ConnectionProvider();
        }
        return instance;
    }

    public Connection getConnection() throws PersistException {

        try {
            this.closeConnection();
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception exception) {
            throw new PersistException(exception);
        }
        return connection;
    }

    public void closeConnection() throws PersistException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                throw new PersistException(sqlException);
            }
        }
    }



}
