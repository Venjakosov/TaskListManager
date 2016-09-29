package com.kosovich.tasks.jdbc.dao;


import java.sql.Connection;

public interface Transaction {

    public Connection getConnection() throws PersistException;

    public void begin() throws PersistException;

    public void commit() throws PersistException;

    public void rollback() throws PersistException;

}