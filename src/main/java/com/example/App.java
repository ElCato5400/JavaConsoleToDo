package com.example;

import java.util.ArrayList;

/**
 * package com.example;
 * 
 * import java.util.ArrayList;
 * import java.util.Date;
 * 
 * public class Task {
 * 
 * // #region Static Attributes
 * public static ArrayList<Task> tasks = new ArrayList<Task>();
 * // #endregion
 * 
 * // #region Attributes
 * private final int id;
 * private String name;
 * private String description;
 * private boolean isDone;
 * private final Date createdDate;
 * // #endregion
 * 
 * // #region Constructors
 * public Task(int id, String name, String description, boolean isDone, Date
 * createdDate) throws Exception {
 * this.id = id;
 * setName(name);
 * setDescription(description);
 * this.isDone = isDone;
 * this.createdDate = createdDate;
 * 
 * tasks.add(this);
 * }
 * 
 * public Task(String name, String description) throws Exception {
 * this(tasks.size() + 1, name, description, false, new Date());
 * }
 * // #endregion
 * 
 * // #region Getters and Setters
 * public void setName(String name) throws Exception {
 * if (name == null || name.isEmpty()) {
 * throw new Exception("Name cannot be null or empty");
 * }
 * this.name = name;
 * }
 * 
 * public void setDescription(String description) throws Exception {
 * if (description == null || description.isEmpty()) {
 * throw new Exception("Description cannot be null or empty");
 * }
 * this.description = description;
 * }
 * 
 * public void setIsDone(boolean isDone) {
 * this.isDone = isDone;
 * }
 * 
 * public int getId() {
 * return id;
 * }
 * 
 * public String getName() {
 * return name;
 * }
 * 
 * public String getDescription() {
 * return description;
 * }
 * 
 * public boolean getIsDone() {
 * return isDone;
 * }
 * 
 * public Date getCreatedDate() {
 * return createdDate;
 * }
 * // #endregion
 * 
 * // #region Methods
 * 
 * public void switchIsDone() {
 * isDone = !isDone;
 * }
 * 
 * public void update(String name, String description) throws Exception {
 * setName(name);
 * setDescription(description);
 * }
 * 
 * public void delete() {
 * tasks.remove(this);
 * }
 * 
 * public static Task getTaskById(int id) {
 * for (Task task : tasks) {
 * if (task.getId() == id) {
 * return task;
 * }
 * }
 * return null;
 * }
 * 
 * @Override
 *           public String toString() {
 *           // return a string representation of the object, including the
 *           // id, name, description, isDone, and createdDate
 *           return String.format("Id: %d, Name: %s, Description: %s, IsDone:
 *           %b, CreatedDate: %s", id, name, description,
 *           isDone, createdDate);
 *           }
 *           // #endregion
 * 
 *           // #region Static Methods
 * 
 *           public static ArrayList<Task> getTasks() {
 *           return tasks;
 *           }
 * 
 *           public static ArrayList<Task> getTasksByIsDone(boolean isDone) {
 *           ArrayList<Task> tasksByIsDone = new ArrayList<Task>();
 *           for (Task task : tasks) {
 *           if (task.getIsDone() == isDone) {
 *           tasksByIsDone.add(task);
 *           }
 *           }
 *           return tasksByIsDone;
 *           }
 * 
 *           // #endregion
 *           }
 * 
 */
public class App {
    public static void printWelcomeMessage() {
        System.out.println("Welcome to the Task Manager!");
    }

    public static void printCommands() {
        System.out.println("Commands:");
        System.out.println();
        System.out.println("add - Add a new task");
        System.out.println("mark - Switch a task's status to done or not done");
        System.out.println();
        System.out.println("list - List all tasks that are not done");
        System.out.println("list all - List all tasks");
        System.out.println();
        System.out.println("update - Update an existing task");
        System.out.println("delete - Delete an existing task");
        System.out.println();
        System.out.println("exit - Exit the program");
    }

    public static void printTasks(ArrayList<Task> tasks) {
        if (tasks.size() == 0) {
            System.out.println("No tasks found");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public static void handleUserInput(String input) {
        switch (input.toLowerCase().trim()) {
            case "add":
                System.out.print("Enter task name >> ");
                String name = System.console().readLine();
                System.out.print("Enter task description >> ");
                String description = System.console().readLine();
                Task task;
                try {
                    task = new Task(name, description);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
                System.out.println("Task: '" + task.getName() + "' added successfully");
                break;
            case "mark":
                int id = readInt("Enter task id >> ");
                Task taskToMarkAsDone = Task.getTaskById(id);
                if (taskToMarkAsDone == null) {
                    System.out.println("Task with id " + id + " not found");
                    break;
                }
                taskToMarkAsDone.switchIsDone();
                System.out.println("Task: '" + taskToMarkAsDone.getName() + "' marked as "
                        + (taskToMarkAsDone.getIsDone() ? "done" : "not done"));
                break;
            case "list":
                System.out.println();
                ArrayList<Task> tasks = Task.getTasksByIsDone(false);
                printTasks(tasks);
                System.out.println();
                break;
            case "list all":
                System.out.println();
                printTasks(Task.getTasks());
                System.out.println();
                break;
            case "update":
                int idToUpdate = readInt("Enter task id >> ");
                Task taskToUpdate = Task.getTaskById(idToUpdate);
                if (taskToUpdate == null) {
                    System.out.println("Task with id " + idToUpdate + " not found");
                    break;
                }
                System.out.print("Enter new task name >> ");
                String newName = System.console().readLine();
                System.out.print("Enter new task description >> ");
                String newDescription = System.console().readLine();
                try {
                    taskToUpdate.update(newName, newDescription);
                } catch (Exception e) {
                    System.out.println("Task name and description cannot be empty");
                    break;
                }
                System.out.println("Task: '" + taskToUpdate.getName() + "' updated successfully");
                break;
            case "delete":
                int idToDelete = readInt("Enter task id >> ");
                Task taskToDelete = Task.getTaskById(idToDelete);
                if (taskToDelete == null) {
                    System.out.println("Task with id " + idToDelete + " not found");
                    break;
                }
                taskToDelete.delete();
                System.out.println("Task: '" + taskToDelete.getName() + "' deleted successfully");
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    public static void main(String[] args) {
        Task.loadAll();
        System.out.println();
        printWelcomeMessage();
        System.out.println();
        printCommands();
        System.out.println();
        while (true) {
            System.out.print("Enter command >> ");
            String input = System.console().readLine();
            handleUserInput(input);
            Task.saveAll();
        }
    }

    public static int readInt(String message) {
        int number;
        while (true) {
            System.out.print(message);
            String input = System.console().readLine();
            try {
                number = Integer.parseInt(input);
                break;
            } catch (Exception e) {
                System.out.println("Invalid number");
            }
        }
        return number;
    }
}
