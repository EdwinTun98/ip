package storage;

import task.Task;
import task.Todo;
import task.Deadline;
import task.Event;
import tasklist.TaskList;
import command.EddieException;
import ui.ErrorMessages;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Storage {
    private final String filePath;
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
                LocalDateTime by = LocalDateTime.parse(parts[3], dateTimeFormat);
                return new Deadline(description, by, isDone);
            case "E":
                LocalDateTime from = LocalDateTime.parse(parts[3], dateTimeFormat);
                LocalDateTime to = LocalDateTime.parse(parts[4], dateTimeFormat);
                return new Event(description, from, to, isDone);
            default:
                throw new EddieException(ErrorMessages.TASK_TYPE_UNKNOWN + type);
            }
        } catch (Exception e) {
            throw new EddieException(ErrorMessages.TASK_PARSING_ERROR + line);
        }
    }

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
