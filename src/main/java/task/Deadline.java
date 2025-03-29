package task;

/**
 * Represents a deadline task.
 */
public class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String getTaskType() {
        return "[D]";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
