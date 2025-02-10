import java.util.Scanner;

public class Eddie {
    private boolean isRunning = true;
    private static final int MAX_TASKS = 100;
    private String[] tasks = new String[MAX_TASKS];
    private int countTask = 0;

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
        switch (input.toLowerCase()) {
        case "bye":
            isRunning = false;
            Display.showEndPage();
            break;
        case "list":
            Display.showTaskList(tasks, countTask);
            break;
        default:
            addTask(input);
            break;
        }
        Display.printLine();
    }

    private void addTask(String task) {
        if (countTask < MAX_TASKS) {
            tasks[countTask] = task;
            countTask++;
            Display.showTaskAdded(tasks[countTask -1]);
        }
        else {
            Display.showError("You have reached the maximum number of tasks");
        }
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
                + "Type 'list' to view your tasks or 'bye' to exit.\n";

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

    public static void showTaskAdded(String task) {
        System.out.println("Eddie:\nTask added: " + task);
    }

    public static void showTaskList(String[] tasks, int countTask) {
        System.out.println("Eddie:\nHere are your tasks:");

        if (countTask == 0) {
            System.out.println("No tasks added yet.");
        }
        else {
            for (int i = 0; i < countTask; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
            }
        }
    }

    public static void showError(String message) {
        System.out.println("Eddie:\nError: " + message);
    }
}

