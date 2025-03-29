package task;

/**
 * Represents a To-Do task.
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String getTaskType() {
        return "[T]";
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
