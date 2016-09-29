package com.kosovich.tasks.jdbc.dao;

import com.kosovich.tasks.jdbc.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TaskDaoJdbcImpl implements TaskDao {

    private static TaskDao instance = new TaskDaoJdbcImpl();

    public static TaskDao getInstance() {
        return instance;
    }

    @Override
    public void create(Task task) throws PersistException {
        Connection conn = TransactionJdbcImpl.getInstance().getConnection();
        try {
            String query;
            if(task.getFinished() == null || equals(false)) {
                query = "insert into task (task_name, target_date, priority, overdue) " +
                        "values (?, ?, ?, ?)";
            }else{
                query = "insert into completed_task (task_name, complete_date, priority, overdue) " +
                        "values (?, ?, ?, ?)";
            }
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, task.getName());
            statement.setTimestamp(2, task.getDate());
            statement.setInt(3, task.getPriority());
            statement.setBoolean(4, task.getOverdue());
            statement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new PersistException(sqlException);
        }
    }

    @Override
    public List<Task> getCompletedTasks() throws PersistException {
        List<Task> list = new ArrayList<>();
        try {
            String query = "select * from completed_task";
            PreparedStatement statement = ConnectionProvider.getInstance().getConnection()
                    .prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();

                task.setId(resultSet.getInt("completed_task_id"));
                task.setPriority(resultSet.getInt("priority"));
                task.setOverdue(resultSet.getBoolean("overdue"));
                task.setDate(resultSet.getTimestamp("complete_date"));
                task.setName(resultSet.getString("task_name"));
                task.setFinished(true);
                list.add(task);
            }
        } catch (SQLException sqlException) {
            throw new PersistException(sqlException);
        }
        return list;
    }

    @Override
    public List<Task> getRegularTasks() throws PersistException{
        List<Task> list = new ArrayList<>();
        try {
            Transaction tx = TransactionJdbcImpl.getInstance();
            Connection conn = tx.getConnection();
            String query = "select * from task";
            PreparedStatement statement = conn
                    .prepareStatement(query);
            tx.begin();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("task_id"));
                task.setPriority(resultSet.getInt("priority"));
                task.setDate(resultSet.getTimestamp("target_date"));
                task.setName(resultSet.getString("task_name"));
                task.setFinished(false);
                if(task.getDate().getTime() < System.currentTimeMillis()){
                    task.setOverdue(true);
                    setOverdued(task);
                }else{
                    task.setOverdue(false);
                }
                list.add(task);
            }
            tx.commit();
            Collections.sort(list, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    if(o1.getDate().getTime() <= o2.getDate().getTime()){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });
        } catch (SQLException sqlException) {
            throw new PersistException(sqlException);
        }
        return list;
    }

    @Override
    public void deleteFromTask(Task task) throws PersistException {
        try {
            String query = "delete from task where task_id = ?";
            PreparedStatement statement = TransactionJdbcImpl.getInstance().getConnection()
                    .prepareStatement(query);

            statement.setInt(1, task.getId());
            statement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new PersistException(sqlException);
        }
    }

    @Override
    public void moveToCompleted(Task task) throws PersistException {
        if(task.getFinished().equals(true)){
            return;
        }else{
            Transaction tx = TransactionJdbcImpl.getInstance();
            Connection conn = tx.getConnection();
            try {
                tx.begin();

                deleteFromTask(task);
                task.setFinished(true);
                create(task);

                tx.commit();
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException sqlException) {
                    throw new PersistException(sqlException);
                }
            }

        }
    }

    public Task readTaskById(Integer id) throws PersistException{
        if (id == null) {
            throw new IllegalArgumentException("ID is null");
        }
        Task task = null;
        try {
            Connection c = ConnectionProvider.getInstance().getConnection();
            String query = "select * from task where task_id = ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                task =  new Task();
                task.setId(id);
                task.setPriority(resultSet.getInt("priority"));
                task.setDate(resultSet.getTimestamp("target_date"));
                task.setName(resultSet.getString("task_name"));
                task.setFinished(false);
                task.setOverdue(resultSet.getBoolean("overdue"));
            }
        } catch (SQLException sqlException) {
            throw new PersistException(sqlException);
        }
        return task;
    }

    public void setOverdued(Task task) throws PersistException{
        try {
            String query = "update task set overdue = true where task_id = ?";
            PreparedStatement statement =  TransactionJdbcImpl.getInstance().getConnection()
                    .prepareStatement(query);
            statement.setInt(1, task.getId());
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new PersistException(sqlException);
        }
    }

}
