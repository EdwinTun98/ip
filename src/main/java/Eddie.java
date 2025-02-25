import java.util.Scanner;
import java.util.ArrayList;

/**
 * The Eddie class represents a simple task management system chatbot.
 * It allows users to add, list, mark, and unmark tasks through a CLI.
 */
public class Eddie {
    private boolean isRunning = true;
    private ArrayList<Task> tasksList;

    public Eddie() {
        this.tasksList = Storage.loadTasks(); // Load tasks from file at startup
    }

    /**
     * Initializes Eddie and starts the run loop.
     */
    public static void main(String[] args) {
        Eddie eddie = new Eddie();
        eddie.run();
    }

    /**
     * Starts the application and continuously processes user input.
     */
    public void run() {
        Display.showStartPage();
        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            System.out.print("You: ");
            String userInput = scanner.nextLine().trim();
            Display.printLine();
            processUserInput(userInput);
        }
        scanner.close();
    }

    /**
     * Processes user input and executes the user command through CLI.
     *
     * @param input The user's command input.
     */
    private void processUserInput(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String arguments = (parts.length > 1) ? parts[1] : "";
        try {
            switch (command) {
            case "bye":
                isRunning = false;
                Display.showEndPage();
                break;
            case "list":
                Display.showTaskList(tasksList);
                break;
            case "mark":
                taskStatus(arguments, true);
                break;
            case "unmark":
                taskStatus(arguments, false);
                break;
            case "todo":
                addToDo(arguments);
                break;
            case "deadline":
                addDeadLine(arguments);
                break;
            case "event":
                addEvent(arguments);
                break;
            case "delete":
                deleteTask(arguments);
                break;
            default:
                throw new EddieException(ErrorMessages.INVALID_COMMAND);
            }
        } catch (EddieException e) {
            Display.showError(e.getMessage());
        }
        Display.printLine();
    }

    /**
     * Adds a ToDo task to the task list.
     *
     * @param details The task description.
     * @throws EddieException If the details are empty or invalid.
     */
    private void addToDo(String details) throws EddieException {
        EddieException.checkTodo(details);
        Task newTask = new Todo(details);
        tasksList.add(newTask);
        Display.showTaskAdded(newTask, tasksList.size());
        Storage.saveTasks(tasksList);  // Save to file after adding
    }

    /**
     * Adds a Deadline task to the task list.
     *
     * @param details The task description and deadline.
     * @throws EddieException If the format is incorrect.
     */
    private void addDeadLine(String details) throws EddieException {
        EddieException.checkDeadline(details);
        String[] deadlineParts = details.split(" /by ", 2);
        Task newTask = new Deadline(deadlineParts[0], deadlineParts[1]);
        tasksList.add(newTask);
        Display.showTaskAdded(newTask, tasksList.size());
        Storage.saveTasks(tasksList);
    }

    /**
     * Adds an Event task to the task list.
     *
     * @param details The task description, start, and end times.
     * @throws EddieException If the format is incorrect.
     */
    private void addEvent(String details) throws EddieException {
        EddieException.checkEvent(details);
        String[] eventParts = details.split(" /from ", 2);
        String[] timeParts = eventParts[1].split(" /to ", 2);
        Task newTask = new Event(eventParts[0], timeParts[0], timeParts[1]);
        tasksList.add(newTask);
        Display.showTaskAdded(newTask, tasksList.size());
        Storage.saveTasks(tasksList);
    }

    /**
     * Marks or unmarks a task as completed.
     *
     * @param taskIndex The task number as a string.
     * @param markDone  True to mark as done, false to unmark.
     * @throws EddieException If the task number is invalid.
     */
    private void taskStatus(String taskIndex, boolean markDone) throws EddieException {
        int index = EddieException.checkTaskStatus(taskIndex, tasksList.size());
        Task task = tasksList.get(index);
        if (markDone) {
            task.markDone();
        } else {
            task.markNotDone();
        }
        Display.showMarkedStatus(task, markDone);
        Storage.saveTasks(tasksList);
    }

    private void deleteTask(String taskIndex) throws EddieException {
        int index = EddieException.checkTaskStatus(taskIndex, tasksList.size());
        Task removedTask = tasksList.remove(index);
        Display.showTaskDeleted(removedTask, tasksList.size());
        Storage.saveTasks(tasksList);
    }
}

/**
 * Represents a generic task that can be marked as done or not done.
 */
abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    public String getIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    public String getDescription() {
        return description;
    }

    public abstract String getTaskType();

    public abstract String toFileFormat();

    @Override
    public String toString() {
        return getTaskType() + getIcon() + " " + getDescription();
    }
}

/**
 * Todo task class to create todo tasks.
 */
class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String getTaskType() {
        return "[T]";
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}

/**
 * deadline task class to create deadline tasks.
 */
class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String getTaskType() {
        return "[D]";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}

/**
 * event task class to create events with a start and end time.
 */
class Event extends Task {
    private String from, to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, String from, String to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskType() {
        return "[E]";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

/**
 * Eddie exception class for handling task-related errors in the Eddie task manager.
 */
class EddieException extends Exception {
    /**
     * Constructs an EddieException with a specific error message.
     *
     * @param message The error message.
     */
    public EddieException(String message) {
        super(message);
    }

    /**
     * Validates a ToDo task description.
     *
     * @param details The task description.
     * @throws EddieException If the description is empty.
     */
    public static void checkTodo(String details) throws EddieException {
        if (details.isEmpty()) {
            throw new EddieException(ErrorMessages.INVALID_TODO);
        }
    }

    /**
     * Validates a Deadline task description.
     *
     * @param details The task description including the deadline.
     * @throws EddieException If the format is incorrect.
     */
    public static void checkDeadline(String details) throws EddieException {
        String[] deadlineParts = details.split(" /by ", 2);
        if (deadlineParts.length < 2) {
            throw new EddieException(ErrorMessages.INVALID_DEADLINE);
        }
    }

    /**
     * Validates an Event task description.
     *
     * @param details The task description including the event time.
     * @throws EddieException If the format is incorrect.
     */
    public static void checkEvent(String details) throws EddieException {
        String[] eventParts = details.split(" /from ", 2);
        if (eventParts.length < 2 || !eventParts[1].contains(" /to ")) {
            throw new EddieException(ErrorMessages.INVALID_EVENT);
        }
    }

    /**
     * Validates and parses a task index input.
     *
     * @param taskIndex The task index as a string.
     * @param taskCount The total number of tasks.
     * @return The parsed and validated task index.
     * @throws EddieException If the task number is invalid.
     */
    public static int checkTaskStatus(String taskIndex, int taskCount) throws EddieException {
        try {
            int index = Integer.parseInt(taskIndex) - 1;
            if (index < 0 || index >= taskCount) {
                throw new EddieException(ErrorMessages.INVALID_TASK_NUMBER);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new EddieException(ErrorMessages.INVALID_TASK_NUMBER);
        }
    }
}

/**
 * Contains predefined error messages for the Eddie task manager.
 */
class ErrorMessages {
    public static final String INVALID_COMMAND = "Oh no! Invalid command. Use: " +
            "todo, deadline, event, list, mark {num}, or unmark {num}.";
    public static final String INVALID_TODO = "Oh no! empty description. Use:" +
            " todo {task description}";
    public static final String INVALID_DEADLINE = "Oh no! empty description. " +
            "Use: deadline {task} /by {date}";
    public static final String INVALID_EVENT = "Oh no! empty description. Use:" +
            " event {task} /from {start} /to {end}";
    public static final String INVALID_TASK_NUMBER = "Oh no! Invalid task number.";
    public static final String MKDIR_FAILED = "Oh no! Unable to create directory" +
            " for saving task.";
    public static final String SAVE_TASK_FAILED = "Oh no! Unable to save task: ";
    public static final String TASK_LOAD_FAILED = "Oh no! Unable to load task: ";
    public static final String TASK_TYPE_UNKNOWN = "Oh no! Unknown task type: ";
    public static final String TASK_PARSING_ERROR = "Oh no! parsing task: ";
}

/**
 * The Display class to print user interface messages
 * for the Eddie task manager, including startup, shutdown, task actions,
 * and error messages.
 */
class Display {
    private static final String LOGO =
            "  _____  ____  ____  ___  _____  \n"
                    + " | ____||  _ \\|  _ \\|_ _|| ____| \n"
                    + " |  _|  | | | | | | || | |  _|   \n"
                    + " | |___ | |_| | |_| || | | |___  \n"
                    + " |_____||____/|____/|___||_____| \n";

    /**
     * Displays the startup message and user guide.
     */
    public static void showStartPage() {
        System.out.println(LOGO);
        printLine();
        System.out.println("Eddie: Welcome to your personal task manager!");
        System.out.println("I can help you manage different types of tasks.\n");

        System.out.println("HOW TO USE EDDIE by typing");
        System.out.println("Adding a Task!");
        System.out.println("To add a ToDo: `todo {Name of Task}`");
        System.out.println("To add a Deadline: `deadline {Name of Deadline} /by {Date or Day}`");
        System.out.println("To add an Event: `event {Name of Event} /from {Day/Date} {Time} /to {Time}`\n");

        System.out.println("To view your Task List!");
        System.out.println("See all tasks: `list`\n");

        System.out.println("Managing Tasks!");
        System.out.println("To mark as done: `mark 2`");
        System.out.println("To unmark: `unmark 2`\n");

        System.out.println("Exiting the Program!");
        System.out.println("To exit: `bye`\n");
        printLine();
    }

    /**
     * Displays the shutdown message.
     */
    public static void showEndPage() {
        System.out.println(LOGO);
        printLine();
        System.out.println("Eddie:\nGoodbye! Hope to see you again soon!");
        printLine();
    }

    /**
     * Prints a horizontal line as a separator.
     */
    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message confirming a task has been added.
     *
     * @param task  The task that was added.
     * @param count The total number of tasks.
     */
    public static void showTaskAdded(Task task, int count) {
        System.out.println("Eddie:\nGot it. I've added this task:\n  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Displays the list of all tasks.
     *
     * @param tasksList Array of tasks.
     */
    public static void showTaskList(ArrayList<Task> tasksList) {
        System.out.println("Eddie:\nHere are the tasks in your list:");
        if (tasksList.isEmpty()) {
            System.out.println("No tasks in your list.");
        }

        for (int i = 0; i < tasksList.size(); i++) System.out.println((i + 1) + ". " + tasksList.get(i));
    }

    /**
     * Displays a message when a task is marked or unmarked.
     *
     * @param task The task being updated.
     * @param done True if marking as done, false if unmarking.
     */
    public static void showMarkedStatus(Task task, boolean done) {
        if (done) {
            System.out.println("Eddie:\nYay! Marked as done:\n  " + task + "\nHave a Beer!!");
        } else {
            System.out.println("Eddie:\nOh no! Marked as not done:\n  " + task);
        }
    }

    public static void showTaskDeleted(Task task, int count) {
        System.out.println("Eddie:\nNoted. I've removed this task:\n  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Displays an error message.
     *
     * @param msg The error message to display.
     */
    public static void showError(String msg) {
        System.out.println("Eddie:\nError: " + msg);
    }
}
