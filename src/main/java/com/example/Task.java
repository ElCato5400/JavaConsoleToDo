package com.example;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.json.*;

public class Task {

    // #region Static Attributes
    public static ArrayList<Task> tasks = new ArrayList<Task>();
    // #endregion

    // #region Attributes
    private final int id;
    private String name;
    private String description;
    private boolean isDone;
    private final Date createdDate;
    // #endregion

    // #region Constructors
    public Task(int id, String name, String description, boolean isDone, Date createdDate) throws Exception {
        this.id = id;
        setName(name);
        setDescription(description);
        this.isDone = isDone;
        this.createdDate = createdDate;

        tasks.add(this);
    }

    public Task(String name, String description) throws Exception {
        this(tasks.size() + 1, name, description, false, new Date());
    }
    // #endregion

    // #region Getters and Setters
    public void setName(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new Exception("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setDescription(String description) throws Exception {
        if (description == null || description.isEmpty()) {
            throw new Exception("Description cannot be null or empty");
        }
        this.description = description;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    // #endregion

    // #region Methods

    public void switchIsDone() {
        isDone = !isDone;
    }

    public void update(String name, String description) throws Exception {
        setName(name);
        setDescription(description);
    }

    public void delete() {
        tasks.remove(this);
    }

    public static Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        // return a string representation of the object, including the
        // id, name, description, isDone, and createdDate
        // use a fixed width for each field, and left-justify the fields
        // (see the sample output for an example)
        return String.format("|%-5d|%-20s|%-50s|%-10s|%-20s", id, name, description, isDone, createdDate);
    }
    // #endregion

    // #region Static Methods

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static ArrayList<Task> getTasksByIsDone(boolean isDone) {
        ArrayList<Task> tasksByIsDone = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.getIsDone() == isDone) {
                tasksByIsDone.add(task);
            }
        }
        return tasksByIsDone;
    }

    public static void saveAll() {
        try {
            File file = new File("tasks.json");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            JSONArray jsonArray = new JSONArray();
            for (Task task : tasks) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", task.getId());
                jsonObject.put("name", task.getName());
                jsonObject.put("description", task.getDescription());
                jsonObject.put("isDone", task.getIsDone());
                jsonObject.put("createdDate", task.getCreatedDate().getTime());
                jsonArray.put(jsonObject);
            }
            fileWriter.write(jsonArray.toString());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadAll() {
        try {
            File file = new File("tasks.json");
            if (file.exists()) {
                String jsonString = "";
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    jsonString += scanner.nextLine();
                }
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    new Task(jsonObject.getInt("id"), jsonObject.getString("name"),
                            jsonObject.getString("description"), jsonObject.getBoolean("isDone"),
                            new Date(jsonObject.getLong("createdDate")));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // #endregion
}
