package command;

import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

/**
 * Abstract base class for all user commands.
 */
public abstract class Command {
    /**
     * Executes the command with the provided task list, UI, and storage.
     *
     * @param tasks   The task list to operate on.
     * @param ui      The UI to interact with the user.
     * @param storage The storage to save and load data.
     * @throws EddieException for error occurs during command execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws EddieException;

    public boolean isExit() {
        return false;
    }
}


