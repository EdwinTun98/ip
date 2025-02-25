import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH = "./data/eddie.txt";
    private static final String DIRECTORY_PATH = "./data/";

    /**
     * Saves the tasks to a file.
     *
     * @param tasks The list of tasks to save.
     */
    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            File directory = new File(DIRECTORY_PATH);

            if (directory.exists() || directory.mkdir()) {
                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    for (Task task : tasks) {
                        writer.write(task.toFileFormat() +
                                System.lineSeparator());
                    }
                }
            } else {
                System.out.println(ErrorMessages.MKDIR_FAILED);
            }
        } catch (IOException e) {
            System.out.println(ErrorMessages.SAVE_TASK_FAILED + e.getMessage());
        }
    }

    /**
     * Loads tasks from a file.
     *
     * @return The list of tasks loaded from the file.
     */
    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println(ErrorMessages.TASK_LOAD_FAILED +
                        e.getMessage());
            }
        } else {
            System.out.println("Oh no! saved tasks found. " +
                    "Creating a new one in a sec.");
        }

        return tasks;
    }


    /**
     * Parses a line from the file and converts it into a Task object.
     *
     * @param line The line from the file.
     * @return The corresponding Task object.
     */
    private static Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            return switch (type) {
                case "T" -> new Todo(description, isDone);
                case "D" -> new Deadline(description, parts[3], isDone);
                case "E" -> new Event(description, parts[3], parts[4], isDone);
                default -> {
                    System.out.println(ErrorMessages.TASK_TYPE_UNKNOWN + type);
                    yield null;
                }
            };
        } catch (Exception e) {
            System.out.println(ErrorMessages.TASK_PARSING_ERROR + line);
            return null;
        }
    }
}
