import java.util.Scanner;

/**
 * The Eddie class represents a simple task management system chatbot.
 * It allows users to add, list, mark, and unmark tasks through a CLI.
 */
public class Eddie {
    private boolean isRunning = true;
    private static final int MAX_TASKS = 100;
    private final Task[] tasks = new Task[MAX_TASKS];
    private int taskCount = 0;

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
                Display.showTaskList(tasks, taskCount);
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
     * @param details The task description.
     * @throws EddieException If the details are empty or invalid.
     */
    private void addToDo(String details) throws EddieException {
        EddieException.checkTodo(details);
        Task newTask = new Todo(details);
        tasks[taskCount++] = newTask;
        Display.showTaskAdded(newTask, taskCount);
    }

    /**
     * Adds a Deadline task to the task list.
     * @param details The task description and deadline.
     * @throws EddieException If the format is incorrect.
     */
    private void addDeadLine(String details) throws EddieException {
        EddieException.checkDeadline(details);
        String[] deadlineParts = details.split(" /by ", 2);
        Task newTask = new Deadline(deadlineParts[0], deadlineParts[1]);
        tasks[taskCount++] = newTask;
        Display.showTaskAdded(newTask, taskCount);
    }

    /**
     * Adds an Event task to the task list.
     * @param details The task description, start, and end times.
     * @throws EddieException If the format is incorrect.
     */
    private void addEvent(String details) throws EddieException {
        EddieException.checkEvent(details);
        String[] eventParts = details.split(" /from ", 2);
        String[] timeParts = eventParts[1].split(" /to ", 2);
        Task newTask = new Event(eventParts[0], timeParts[0], timeParts[1]);
        tasks[taskCount++] = newTask;
        Display.showTaskAdded(newTask, taskCount);
    }

    /**
     * Marks or unmarks a task as completed.
     * @param taskIndex The task number as a string.
     * @param markDone True to mark as done, false to unmark.
     * @throws EddieException If the task number is invalid.
     */
    private void taskStatus(String taskIndex, boolean markDone) throws EddieException {
            int index = EddieException.checkTaskStatus(taskIndex, taskCount);
            if (markDone) {
                tasks[index].markDone();
            } else {
                tasks[index].markNotDone();
            }
            Display.showMarkedStatus(tasks[index], markDone);
    }
}

/**
 * Represents a generic task that can be marked as done or not done.
 */
class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with a given description.
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {this.isDone = true;}

    /**
     * Marks the task as not done.
     */
    public void markNotDone() {this.isDone = false;}

    /**
     * Returns the task's completion status icon.
     * @return "[X]" if done, "[ ]" otherwise.
     */
    public String getIcon() {return isDone ? "[X]" : "[ ]";}

    /**
     * Returns the task description.
     * @return The description of the task.
     */
    public String getDescription() {return description;}

    /**
     * Returns the task type icon.
     * @return A default task type icon.
     */
    public String getTaskType() {return "[ ]";}

    @Override
    public String toString() {return getTaskType() + getIcon() + " " + getDescription();}
}

/**
 * Todo task class to create todo tasks.
 */
class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getTaskType() {
        return "[T]";
    }
}

/**
 * deadline task class to create deadline tasks.
 */
class Deadline extends Task {
    private final String by;

    /**
     * Constructs a new Deadline task.
     * @param description The task description.
     * @param by The deadline for the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTaskType() {
        return "[D]";
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
    private final String from;
    private final String to;

    /**
     * Constructs a new Event task.
     * @param description The event description.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskType() {
        return "[E]";
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
     * @param message The error message.
     */
    public EddieException(String message) {
        super(message);
    }

    /**
     * Validates a ToDo task description.
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
     * @param task The task that was added.
     * @param count The total number of tasks.
     */
    public static void showTaskAdded(Task task, int count) {
        System.out.println("Eddie:\nGot it. I've added this task:\n  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Displays the list of all tasks.
     * @param tasks Array of tasks.
     * @param count Total number of tasks.
     */
    public static void showTaskList(Task[] tasks, int count) {
        System.out.println("Eddie:\nHere are the tasks in your list:");
        for (int i = 0; i < count; i++) System.out.println((i + 1) + ". " + tasks[i]);
    }

    /**
     * Displays a message when a task is marked or unmarked.
     * @param task The task being updated.
     * @param done True if marking as done, false if unmarking.
     */
    public static void showMarkedStatus(Task task, boolean done) {
        if(done) {
            System.out.println("Eddie:\nYay! Marked as done:\n  " + task + "\nHave a Beer!!");
        } else {
            System.out.println("Eddie:\nOh no! Marked as not done:\n  " + task);
        }
    }

    /**
     * Displays an error message.
     * @param msg The error message to display.
     */
    public static void showError(String msg) {
        System.out.println("Eddie:\nError: " + msg);
    }
}
