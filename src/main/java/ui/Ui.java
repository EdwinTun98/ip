package ui;

import task.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String LOGO =
            "  _____  ____  ____  ___  _____  \n"
                    + " | ____||  _ \\|  _ \\|_ _|| ____| \n"
                    + " |  _|  | | | | | | || | |  _|   \n"
                    + " | |___ | |_| | |_| || | | |___  \n"
                    + " |_____||____/|____/|___||_____| \n";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showStartPage() {
        System.out.println(LOGO);
        printLine();
        System.out.println("Eddie: Welcome to your personal task manager!");
        System.out.println("I can help you manage different types of tasks.\n");

        System.out.println("HOW TO USE EDDIE:");
        System.out.println("To add a ToDo: `todo {task}`");
        System.out.println("To add a Deadline: `deadline {task} /by {date}`");
        System.out.println("To add an Event: `event {task} /from {start} /to {end}`\n");

        System.out.println("Other Commands:");
        System.out.println("View all tasks: `list`");
        System.out.println("Mark as done: `mark 2`");
        System.out.println("Unmark: `unmark 2`");
        System.out.println("Delete: `delete 2`");
        System.out.println("Exit: `bye`");
        printLine();
    }

    public void showEndPage() {
        printLine();
        System.out.println("Eddie: Goodbye! Hope to see you again soon!");
        printLine();
    }

    public void showTaskAdded(Task task, int count) {
        System.out.println("Eddie:\nGot it. I've added this task:\n  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int count) {
        System.out.println("Eddie:\nNoted. I've removed this task:\n  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    public void showMarkedStatus(Task task, boolean done) {
        if (done) {
            System.out.println("Eddie:\nYay! Marked as done:\n  " + task + "\nHave a Beer!!");
        } else {
            System.out.println("Eddie:\nOh no! Marked as not done:\n  " + task);
        }
    }

    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("Eddie:\nHere are the tasks in your list:");
        if (tasks.isEmpty()) {
            System.out.println("No tasks in your list.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void showError(String msg) {
        System.out.println("Eddie:\nError: " + msg);
    }

    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    public void showLoadingError() {
        System.out.println("Error loading saved tasks. Starting with an empty task list.");
    }

    public String readCommand() {
        System.out.print("You: ");
        return scanner.nextLine().trim();
    }

    public void showMatchingTasks(List<Task> matchedTasks) {
        System.out.println("Eddie:\nHere are the tasks matching your list:");
        if (matchedTasks.isEmpty()) {
            System.out.println("No tasks matching your list.");
        }
        int index = 1;
        for (Task task : matchedTasks) {
            System.out.println(index + ". " + task);
            index++;
        }
    }
}
