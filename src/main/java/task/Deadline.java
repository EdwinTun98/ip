package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
    private static final DateTimeFormatter saveFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String getTaskType() {
        return "[D]";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(saveFormat);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by.format(displayFormat) + ")";
    }
}
