package command;

import storage.Storage;
import tasklist.TaskList;
import ui.Ui;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute (TaskList tasks, Ui ui, Storage storage) {
        List<Task> matchedTasks = new ArrayList<>();

        for (Task task: tasks.getAllTasks()) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchedTasks.add(task);
            }
        }
        ui.showMatchingTasks(matchedTasks);
    }
}
