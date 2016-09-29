package com.kosovich.tasks.jdbc.dao;

/**
 * Created by Venkaaa on 29.09.2016.
 */
public class PersistException extends Exception {

    private static final long serialVersionUID = -4503812657754261521L;

    public PersistException() {
        super();
    }

    public PersistException(String msg) {
        super(msg);
    }

    public PersistException(Exception exception) {
        super(exception);
    }
}
