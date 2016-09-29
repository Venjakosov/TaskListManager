package com.kosovich.tasks.jdbc;

import com.kosovich.tasks.jdbc.dao.DaoFactory;
import com.kosovich.tasks.jdbc.dao.PersistException;
import com.kosovich.tasks.jdbc.dao.TaskDao;
import com.kosovich.tasks.jdbc.model.Task;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        TaskDao taskDao = DaoFactory.getTaskDao();

        Scanner in = new Scanner(System.in);
        main:
        while (true) {
            try {
                while (true) {
                System.out.println("Выберите действие:");
                System.out.println("1) Добавить задачу");
                System.out.println("2) Вывести список задач");
                System.out.println("3) Выйти");
                String s = in.next();
                int choise = Integer.parseInt(s);
                if(choise != 1 && choise!= 2 && choise!= 3){
                    System.out.println("Повторите ввод");
                    continue;
                }else{

                    switch (choise){
                            case 1: {
                                System.out.println("Создание новой задачи, введите Название задачи:");
                                s = in.next();
                                s = s + in.nextLine();
                                Task newTask = new Task();
                                newTask.setName(s);
                                System.out.println("Введите дату когда задача должна быть выполнена, формат даты - yyyy-MM-dd-HH-mm ");
                                while (true) {
                                    s = in.next();
                                    Date date = null;
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                                    try {
                                        date = format.parse(s);
                                        if(date.getTime() < System.currentTimeMillis()){
                                            System.out.println("Введенная дата должна быть больше сегодняшней");
                                            continue;
                                        }
                                    } catch (ParseException e) {
                                        System.out.println("Неверный формат даты, повторите попытку");
                                        continue;
                                    }
                                    newTask.setDate(new Timestamp(date.getTime()));
                                    break;
                                }
                                System.out.println("Введите приоритет задачи(от 1 до 5):");
                                while (true) {
                                    s = in.next();
                                    int priority;
                                    try {
                                        priority = Integer.parseInt(s);
                                        if (priority > 5 || priority < 1) {
                                            System.out.println("Повторите ввод приоритета:");
                                            continue;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Повторите ввод приоритета:");
                                        continue;
                                    }
                                    newTask.setPriority(priority);
                                    newTask.setOverdue(false);
                                    break;
                                }
                                try {
                                    taskDao.create(newTask);
                                    System.out.println("Задача успешно создана!");

                                    continue;
                                } catch (PersistException p) {
                                    p.printStackTrace();
                                }
                            }
                            case 2: {
                                try {
                                    System.out.println("Незавершенные задачи:");
                                    System.out.println(taskDao.getRegularTasks());
                                    System.out.println("=========================================");
                                    while (true){

                                        System.out.println("Выберите действие которое вы хотите совершить со списком:");
                                        System.out.println("1) Завершить не завершенную задачу с ID");
                                        System.out.println("2) Посмотреть список завершенных задач");
                                        System.out.println("3) Посмотреть список НЕзавершенных задач");
                                        System.out.println("4) В главное меню");
                                        String ins = in.next();
                                        try {
                                            int choise2 = Integer.parseInt(ins);
                                            if (choise2 != 1 && choise2 != 2 && choise2 != 3 && choise2 != 4) {
                                                System.out.println("Повторите ввод");
                                                continue;
                                            }

                                            switch (choise2) {
                                                case 1: {
                                                    System.out.println("Введите ID задачи, которую хотите завершить:");
                                                    while (true) {
                                                        s = in.next();
                                                        int id;
                                                        try {
                                                            id = Integer.parseInt(s);
                                                            taskDao.moveToCompleted(taskDao.readTaskById(id));
                                                            System.out.println("Задача успешно завершена!!!");
                                                            System.out.println("Незавершенные задачи:");
                                                            System.out.println(taskDao.getRegularTasks());
                                                            break;
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("ID введён не верно, повторите попытку: ");
                                                            continue;
                                                        } catch (NullPointerException e){
                                                            System.out.println("ID введён не верно, повторите попытку: ");
                                                            continue;
                                                        }

                                                    }
                                                    break;
                                                }
                                                case 2: {
                                                    System.out.println("Список завершенных задач:");
                                                    System.out.println(taskDao.getCompletedTasks());
                                                    break;
                                                }
                                                case 3:{
                                                    System.out.println("Список незавершенных задач:");
                                                    System.out.println(taskDao.getRegularTasks());
                                                    break;
                                                }

                                                case 4: {
                                                    continue main;
                                                }

                                            }
                                        }catch (NumberFormatException e){
                                            System.out.println("Повторите выбор:");
                                            continue;
                                        }
                                        }



                                } catch (PersistException e) {
                                    e.printStackTrace();
                                }
                            }
                            case 3: {
                                System.exit(0);
                            }
                        }
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Повторите ввод");
                continue;
            }
        }
    }
}
