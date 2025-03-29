package task;

/**
 * Task class that can be marked as done or not done.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the task's completion status icon.
     *
     * @return "[X]" if done, "[ ]" otherwise.
     */
    public String getIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns the task description.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the task type icon.
     *
     * @return A default task type icon.
     */
    public abstract String getTaskType();

    /**
     * Converts the task into a file format.
     *
     * @return A string representation suitable for file storage.
     */
    public abstract String toFileFormat();

    @Override
    public String toString() {
        return getTaskType() + getIcon() + " " + getDescription();
    }
}
