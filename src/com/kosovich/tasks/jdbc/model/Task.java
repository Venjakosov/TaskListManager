package com.kosovich.tasks.jdbc.model;

import java.sql.Timestamp;


public class Task {

    private Integer id;
    private String name;
    private Timestamp date;
    private Integer priority;
    private Boolean overdue;
    private Boolean finished;

    public Task(){
        super();
    }

    public Task(String name, Timestamp date, int priority){
        this.name = name;
        this.date = date;
        this.priority = priority;
        overdue = false;
        finished = false;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Задача: " +
                "Дата выполнения=" + date +
                ", | id=" + id +
                ", | Имя задачи='" + name + '\'' +
                ", | Приоритет=" + priority +
                ", | Просроченная=" + overdue +
                "|\n";
    }
}
