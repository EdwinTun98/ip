package tasklist;

import task.Task;
import command.EddieException;

import java.util.ArrayList;

/**
 * Manages a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with preloaded tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieves a task by its index.
     *
     * @param index The index of the task.
     * @return The task at the given index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Removes a task by its index.
     *
     * @param index The index of the task to remove.
     * @return The removed task.
     */
    public Task removeTask(int index) throws EddieException {
        if (index < 0 || index >= tasks.size()) {
            throw new EddieException("Invalid task number.");
        }
        return tasks.remove(index);
    }

    /**
     * Marks or unmarks a task as done.
     *
     * @param index   The index of the task.
     * @param isDone  True to mark as done, false to unmark.
     */
    public void markTask(int index, boolean isDone) throws EddieException {
        if (index < 0 || index >= tasks.size()) {
            throw new EddieException("Invalid task number.");
        }
        Task task = tasks.get(index);
        if (isDone) {
            task.markDone();
        } else {
            task.markNotDone();
        }
    }

    /**
     * Returns the total number of tasks.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the last added task.
     *
     * @return The most recent task.
     */
    public Task getLastTask() {
        return tasks.get(tasks.size() - 1);
    }

    /**
     * Returns all tasks as a list.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Returns a formatted list of all tasks.
     *
     * @return A string representation of all tasks.
     */
    public String getTaskListString() {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            list.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return list.toString();
    }
}
