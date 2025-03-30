package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private static final DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
    private static final DateTimeFormatter saveFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, LocalDateTime from, LocalDateTime to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskType() {
        return "[E]";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from.format(saveFormat) + " | " + to.format(saveFormat);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from.format(displayFormat) + " to: " + to.format(displayFormat) + ")";
    }
}
