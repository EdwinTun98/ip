package command;

import storage.Storage;
import tasklist.TaskList;
import ui.ErrorMessages;
import ui.Ui;
import task.Todo;
import task.Deadline;
import task.Event;

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
            if (deadlineParts.length < 2) {
                throw new EddieException(ErrorMessages.INVALID_DEADLINE);
            }
            tasks.addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
            break;
        case "event":
            String[] eventParts = taskDetails.split(" /from ", 2);
            if (eventParts.length < 2 || !eventParts[1].contains(" /to ")) {
                throw new EddieException(ErrorMessages.INVALID_EVENT);
            }
            String[] timeParts = eventParts[1].split(" /to ", 2);
            tasks.addTask(new Event(eventParts[0], timeParts[0], timeParts[1]));
            break;
        default:
            throw new EddieException(ErrorMessages.INVALID_COMMAND);
        }

        // Show confirm task to user and save tasks
        ui.showTaskAdded(tasks.getLastTask(), tasks.size());
        storage.saveTasks(tasks.getAllTasks());
    }
}
