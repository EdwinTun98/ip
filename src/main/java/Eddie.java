import command.Command;
import command.EddieException;
import parser.Parser;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

/**
 * The main class for the Eddie chatbot task manager.
 */
public class Eddie {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Initializes Eddie with the default storage file path.
     */
    public Eddie(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.loadTask());
        } catch (EddieException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Starts the Eddie chatbot.
     */
    public void run() {
        ui.showStartPage();
        boolean isRunning = true;

        while (isRunning) {
            String userInput = ui.readCommand();
            ui.printLine();
            try {
                Command command = Parser.parse(userInput);
                command.execute(tasks, ui, storage);
                if (command.isExit()) {
                    isRunning = false;
                }
            } catch (EddieException e) {
                ui.showError(e.getMessage());
            }
            ui.printLine();
        }
    }

    /**
     * Entry point for the program.
     */
    public static void main(String[] args) {
        new Eddie("data/tasks.txt").run();
    }
}
