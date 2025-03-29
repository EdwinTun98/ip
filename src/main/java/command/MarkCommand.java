package command;

import storage.Storage;
import task.Task;
import tasklist.TaskList;
import ui.Ui;


public class MarkCommand extends Command {
    private String taskIndex;
    private boolean isMarking;

    public MarkCommand(String taskIndex, boolean isMarking) {
        this.taskIndex = taskIndex;
        this.isMarking = isMarking;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = Integer.parseInt(taskIndex) - 1;
            Task task = tasks.getTask(index);
            tasks.markTask(index, isMarking);
            ui.showMarkedStatus(task, isMarking);
            storage.saveTasks(tasks.getAllTasks());

        } catch (NumberFormatException | EddieException e) {
            ui.showError("Invalid task number!");
        }
    }
}
