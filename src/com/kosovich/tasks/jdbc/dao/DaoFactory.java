package com.kosovich.tasks.jdbc.dao;


public class DaoFactory {

    public static TaskDao getTaskDao(){
        return TaskDaoJdbcImpl.getInstance();
    }
}
