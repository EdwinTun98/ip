package storage;

import task.Task;
import task.Todo;
import task.Deadline;
import task.Event;
import tasklist.TaskList;
import command.EddieException;
import ui.ErrorMessages;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     */
    public ArrayList<Task> loadTask() throws EddieException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            createFile();
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(parseTask(line));
            }
        } catch (IOException e) {
            throw new EddieException(ErrorMessages.TASK_LOAD_FAILED + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves tasks to the storage file.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(ErrorMessages.SAVE_TASK_FAILED + e.getMessage());
        }
    }

    /**
     * Parses a line from the file into a Task object.
     */
    private Task parseTask(String line) throws EddieException {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            switch (type) {
            case "T":
                return new Todo(description, isDone);
            case "D":
                return new Deadline(description, parts[3], isDone);
            case "E":
                return new Event(description, parts[3], parts[4], isDone);
            default:
                throw new EddieException(ErrorMessages.TASK_TYPE_UNKNOWN + type);
            }
        } catch (Exception e) {
            throw new EddieException(ErrorMessages.TASK_PARSING_ERROR + line);
        }
    }

    /**
     * Creates the storage file and parent directories if they don't exist.
     */
    private void createFile() throws EddieException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new EddieException(ErrorMessages.MKDIR_FAILED);
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new EddieException(ErrorMessages.SAVE_TASK_FAILED + e.getMessage());
        }
    }
}
