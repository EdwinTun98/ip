import java.util.Scanner;

public class Eddie {
    private boolean isRunning = true;
    private static final int MAX_TASKS = 100;
    private Task[] tasks = new Task[MAX_TASKS];
    private int taskCount = 0;

    public static void main(String[] args) {
        Eddie eddie = new Eddie();
        eddie.run();
    }

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

    private void processUserInput(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String arguments = (parts.length > 1) ? parts[1] : "";

        switch (command) {
        case "bye":
            isRunning = false;
            Display.showEndPage();
            break;
        case "list":
            Display.showTaskList(tasks, taskCount);
            break;
        case "mark":
            markTask(arguments);
            break;
        case "unmark":
            unmarkTask(arguments);
            break;
        case "todo":
            addTask(command, arguments);
            break;
        case "deadline":
            addTask(command, arguments);
            break;
        case "event":
            addTask(command, arguments);
            break;
        default:
            Display.showError(ErrorMessages.INVALID_COMMAND);
        }
        Display.printLine();
    }

    private void addTask(String taskType, String details) {
        if (taskCount >= MAX_TASKS) {
            Display.showError(ErrorMessages.TASK_LIST_FULL);
            return;
        }

        Task newTask = null;

        switch (taskType) {
        case "todo":
            if (!details.isEmpty()) {
                newTask = new Todo(details);
            } else {
                Display.showError(ErrorMessages.INVALID_TODO);
            }
            break;
        case "deadline":
            String[] deadlineParts = details.split(" /by ", 2);
            if (deadlineParts.length > 1) {
                newTask = new Deadline(deadlineParts[0], deadlineParts[1]);
            } else {
                Display.showError(ErrorMessages.INVALID_DEADLINE);
            }
            break;
        case "event":
            String[] eventParts = details.split(" /from ", 2);
            if (eventParts.length > 1) {
                String[] timeParts = eventParts[1].split(" /to ", 2);
                if (timeParts.length > 1) {
                    newTask = new Event(eventParts[0], timeParts[0], timeParts[1]);
                } else {
                    Display.showError(ErrorMessages.INVALID_EVENT);
                }
            } else {
                Display.showError(ErrorMessages.INVALID_EVENT);
            }
            break;
        }

        if (newTask != null) {
            tasks[taskCount++] = newTask;
            Display.showTaskAdded(newTask, taskCount);
        }
    }

    private void markTask(String indexStr) {
        try {
            int index = Integer.parseInt(indexStr) - 1;
            if (index >= 0 && index < taskCount) {
                tasks[index].markDone();
                Display.showTaskMarked(tasks[index]);
            } else {
                Display.showError(ErrorMessages.INVALID_TASK_NUMBER);
            }
        } catch (NumberFormatException e) {
            Display.showError(ErrorMessages.INVALID_TASK_NUMBER);
        }
    }

    private void unmarkTask(String indexStr) {
        try {
            int index = Integer.parseInt(indexStr) - 1;
            if (index >= 0 && index < taskCount) {
                tasks[index].markNotDone();
                Display.showTaskUnmarked(tasks[index]);
            } else {
                Display.showError(ErrorMessages.INVALID_TASK_NUMBER);
            }
        } catch (NumberFormatException e) {
            Display.showError(ErrorMessages.INVALID_TASK_NUMBER);
        }
    }
}

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
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

    public String getTaskType() {
        return "[ ]";
    }

    @Override
    public String toString() {
        return getTaskType() + getIcon() + " " + getDescription();
    }
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getTaskType() {
        return "[T]";
    }
}

class Deadline extends Task {
    private String by;

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

class Event extends Task {
    private String from, to;

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

class ErrorMessages {
    public static final String INVALID_COMMAND = "Oh no! Invalid command. Use: " +
            "todo, deadline, event, list, mark {num}, or unmark {num}.";
    public static final String TASK_LIST_FULL = "Oh no! You have reached the " +
            "maximum number of tasks.";
    public static final String INVALID_TODO = "Oh no! Invalid description. Use:" +
            " todo {task description}";
    public static final String INVALID_DEADLINE = "Oh no! Invalid description. " +
            "Use: deadline {task} /by {date}";
    public static final String INVALID_EVENT = "Oh no! Invalid description. Use:" +
            " event {task} /from {start} /to {end}";
    public static final String INVALID_TASK_NUMBER = "Oh no! Invalid task number.";
}

class Display {
    private static final String LOGO =
            "  _____  ____  ____  ___  _____  \n"
                    + " | ____||  _ \\|  _ \\|_ _|| ____| \n"
                    + " |  _|  | | | | | | || | |  _|   \n"
                    + " | |___ | |_| | |_| || | | |___  \n"
                    + " |_____||____/|____/|___||_____| \n";

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

    public static void showEndPage() {
        System.out.println(LOGO);
        printLine();
        System.out.println("Eddie:\nGoodbye! Hope to see you again soon!");
        printLine();
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void showTaskAdded(Task task, int count) {
        System.out.println("Eddie:\nGot it. I've added this task:\n  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    public static void showTaskList(Task[] tasks, int count) {
        System.out.println("Eddie:\nHere are the tasks in your list:");
        for (int i = 0; i < count; i++) System.out.println((i + 1) + ". " + tasks[i]);
    }

    public static void showTaskMarked(Task task) {
        System.out.println("Eddie:\nYay! Marked as done:\n  " + task);
    }

    public static void showTaskUnmarked(Task task) {
        System.out.println("Eddie:\nMarked as not done:\n  " + task);
    }

    public static void showError(String msg) {
        System.out.println("Eddie:\nError: " + msg);
    }
}



