package command;

import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showEndPage();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
