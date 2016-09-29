package com.kosovich.tasks.jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionJdbcImpl implements Transaction {

    private static TransactionJdbcImpl instance = null;

    private Connection connection = null;

    private TransactionJdbcImpl() {
    }

    public static TransactionJdbcImpl getInstance() {
        if(TransactionJdbcImpl.instance == null) {
            TransactionJdbcImpl.instance = new TransactionJdbcImpl();
        }
        return TransactionJdbcImpl.instance;
    }

    public Connection getConnection() throws PersistException {
        try {
            if(this.connection == null || this.connection.isClosed()) {
                this.connection = ConnectionProvider.getInstance().getConnection();
            }
        } catch(SQLException sqlException) {
            throw new PersistException(sqlException);
        }
        return this.connection;
    }

    @Override
    public void begin() throws PersistException {
        try {
            this.getConnection().setAutoCommit(false);
        } catch (SQLException sqlException) {
            throw new PersistException(sqlException);
        }
    }

    @Override
    public void commit() throws PersistException {
        try {
            this.connection.commit();
        } catch (SQLException sqlCommitException) {
            throw new PersistException(sqlCommitException);
        } finally {
            this.close();
        }
    }

    @Override
    public void rollback() throws PersistException {
        try {
            this.connection.rollback();
        } catch (SQLException sqlRollbackException) {
            throw new PersistException(sqlRollbackException);
        } finally {
            this.close();
        }
    }

    private void close() throws PersistException {
        try {
            if(this.connection != null &&
                    !this.connection.isClosed()) {
                ConnectionProvider.getInstance().closeConnection();
                this.connection = null;
            }
        } catch (SQLException sqlException) {
            throw new PersistException(sqlException);
        }
    }

}