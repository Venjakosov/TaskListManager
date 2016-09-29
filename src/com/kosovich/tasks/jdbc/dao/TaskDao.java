package com.kosovich.tasks.jdbc.dao;

import com.kosovich.tasks.jdbc.model.Task;
import java.util.List;


public interface TaskDao {

    public void create(Task task)   throws PersistException;
    public void setOverdued(Task task) throws PersistException;
    public void moveToCompleted(Task task) throws PersistException;
    public void deleteFromTask(Task task) throws PersistException;
    public Task readTaskById(Integer id) throws PersistException;
    public List<Task> getCompletedTasks()  throws PersistException;
    public List<Task> getRegularTasks()    throws PersistException;

}
