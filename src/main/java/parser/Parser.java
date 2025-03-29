package parser;

import command.*;
import command.EddieException;

public class Parser {
    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param input The raw user input.
     * @return A Command object that represents the user's action.
     * @throws EddieException If the input is invalid.
     */
    public static Command parse(String input) throws EddieException {
        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase();
        String arguments = (parts.length > 1) ? parts[1] : "";

        switch (command) {
        case "todo":
            return new AddCommand(arguments, "todo");
        case "deadline":
            return new AddCommand(arguments, "deadline");
        case "event":
            return new AddCommand(arguments, "event");
        case "list":
            return new ListCommand();
        case "mark":
            return new MarkCommand(arguments, true);
        case "unmark":
            return new MarkCommand(arguments, false);
        case "delete":
            return new DeleteCommand(arguments);
        case "bye":
            return new ExitCommand();
        default:
            throw new EddieException("Unknown command! Try: todo, deadline, event, list, mark {num}, unmark {num}, delete {num}, or bye.");
        }
    }
}
