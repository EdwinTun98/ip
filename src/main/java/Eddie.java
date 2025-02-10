import java.util.Scanner;

public class Eddie {
    private boolean isRunning = true;
    private static final int MAX_TASKS = 100;
    private Task[] tasks = new Task[MAX_TASKS]; // Store Task objects
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
        String command = parts[0];

        switch (command.toLowerCase()) {
        case "bye":
            isRunning = false;
            Display.showEndPage();
            break;
        case "list":
            Display.showTaskList(tasks, taskCount);
            break;
        case "mark":
            if (parts.length > 1) {
                markTask(parts[1]);
            } else {
                Display.showError("Please provide a task number to mark.");
            }
            break;
        case "unmark":
            if (parts.length > 1) {
                unmarkTask(parts[1]);
            } else {
                Display.showError("Please provide a task number to unmark.");
            }
            break;
        default:
            addTask(input);
            break;
        }
        Display.printLine();
    }

    private void addTask(String taskName) {
        if (taskCount < MAX_TASKS) {
            tasks[taskCount] = new Task(taskName);
            taskCount++;
            Display.showTaskAdded(taskName);
        } else {
            Display.showError("You have reached the maximum number of tasks");
        }
    }

    private void markTask(String indexStr) {
        int index = Integer.parseInt(indexStr) - 1;
        if (index >= 0 && index < taskCount) {
            tasks[index].markDone();
            Display.showTaskMarked(tasks[index]);
        } else {
            Display.showError("Invalid task number.");
        }

    }

    private void unmarkTask(String indexStr) {
        int index = Integer.parseInt(indexStr) - 1;
        if (index >= 0 && index < taskCount) {
            tasks[index].markNotDone();
            Display.showTaskUnmarked(tasks[index]);
        } else {
            Display.showError("Invalid task number.");
        }
    }
}


class Task {
    private String description;
    private boolean isDone;

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
}

class Display {
    public static void showStartPage() {
        String logo =
                "  _____  ____  ____  ___  _____  \n"
                        + " | ____||  _ \\|  _ \\|_ _|| ____| \n"
                        + " |  _|  | | | | | | || | |  _|   \n"
                        + " | |___ | |_| | |_| || | | |___  \n"
                        + " |_____||____/|____/|___||_____| \n";

        String startScreen = "Eddie:\n"
                + "Hello! I'm Eddie, your friendly assistant.\n"
                + "Type anything to add it as a task!\n"
                + "Use 'list' to view tasks, 'mark {num}' to mark done, and 'unmark {num}' to undo.\n";

        System.out.println(logo);
        printLine();
        System.out.println(startScreen);
        printLine();
    }

    public static void showEndPage() {
        System.out.println("Eddie:\nGoodbye! Hope to see you again soon!");
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void showTaskAdded(String taskName) {
        System.out.println("Eddie:\nTask added: " + taskName);
    }

    public static void showTaskList(Task[] tasks, int taskCount) {
        System.out.println("Eddie:\nHere are the tasks in your list:");

        if (taskCount == 0) {
            System.out.println("No tasks added yet.");
        } else {
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i].getIcon() + " " + tasks[i].getDescription());
            }
        }
    }

    public static void showTaskMarked(Task task) {
        System.out.println("Eddie:\nYay! I've marked this task as done:");
        System.out.println(" " + task.getIcon() + " " + task.getDescription());
    }

    public static void showTaskUnmarked(Task task) {
        System.out.println("Eddie:\nOh no!, I've marked this task as not done yet:");
        System.out.println(" " + task.getIcon() + " " + task.getDescription());
    }

    public static void showError(String message) {
        System.out.println("Eddie:\nError: " + message);
    }
}


