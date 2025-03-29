package command;

import storage.Storage;
import tasklist.TaskList;
import ui.Ui;
import task.Task;

public class DeleteCommand extends Command {
    private String taskIndex;

    public DeleteCommand(String taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = Integer.parseInt(taskIndex) - 1;
            Task removedTask = tasks.removeTask(index);
            ui.showTaskDeleted(removedTask, tasks.size());
            storage.saveTasks(tasks.getAllTasks());

        } catch (NumberFormatException | EddieException e) {
            ui.showError("Invalid task number!");
        }
    }
}
