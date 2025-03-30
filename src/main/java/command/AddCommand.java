package command;

import storage.Storage;
import tasklist.TaskList;
import ui.ErrorMessages;
import ui.Ui;
import task.Todo;
import task.Deadline;
import task.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a command to add a new task todo, deadline and event.
 */
public class AddCommand extends Command {
    private final String taskDetails;
    private final String taskType;

    public AddCommand(String taskDetails, String taskType) {
        this.taskDetails = taskDetails;
        this.taskType = taskType;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EddieException {
        switch (taskType) {
        case "todo":
            if (taskDetails.isEmpty()) {
                throw new EddieException(ErrorMessages.INVALID_TODO);
            }
            tasks.addTask(new Todo(taskDetails));
            break;
        case "deadline":
            String[] deadlineParts = taskDetails.split(" /by ", 2);
            if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()) {
                throw new EddieException(ErrorMessages.INVALID_DEADLINE);
            }
            try {
                DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                LocalDateTime by = LocalDateTime.parse(deadlineParts[1].trim(), inputFormat);
                tasks.addTask(new Deadline(deadlineParts[0].trim(), by));
            } catch (DateTimeParseException e) {
                throw new EddieException(ErrorMessages.INVALID_DEADLINE);
            }
            break;
        case "event":
            String[] eventParts = taskDetails.split(" /from ", 2);
            if (eventParts.length < 2 || !eventParts[1].contains(" /to ") || eventParts[0].trim().isEmpty()) {
                throw new EddieException(ErrorMessages.INVALID_EVENT);
            }
            String[] timeParts = eventParts[1].split(" /to ", 2);
            try {
                DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                LocalDateTime from = LocalDateTime.parse(timeParts[0].trim(), inputFormat);
                LocalDateTime to = LocalDateTime.parse(timeParts[1].trim(), inputFormat);
                tasks.addTask(new Event(eventParts[0].trim(), from, to));
            } catch (DateTimeParseException e) {
                throw new EddieException(ErrorMessages.INVALID_EVENT);
            }
            break;
        default:
            throw new EddieException(ErrorMessages.INVALID_COMMAND);
        }

        // Show confirm task to user and save tasks
        ui.showTaskAdded(tasks.getLastTask(), tasks.size());
        storage.saveTasks(tasks.getAllTasks());
    }
}
