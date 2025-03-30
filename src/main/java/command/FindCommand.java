package command;

import storage.Storage;
import tasklist.TaskList;
import ui.Ui;
import task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds and displays tasks that descriptions contain a given keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword to search for.
     *
     * @param keyword The keyword used to search task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching task list for tasks that contain the same keyword
     * in their description, and displays the matching tasks using the UI.
     *
     * @param tasks   The TaskList containing the user's tasks.
     * @param ui      The Ui instance to handle user interaction.
     * @param storage The Storage instance (not used here, but required by method signature).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> matchedTasks = new ArrayList<>();

        for (Task task : tasks.getAllTasks()) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchedTasks.add(task);
            }
        }

        ui.showMatchingTasks(matchedTasks);
    }
}
