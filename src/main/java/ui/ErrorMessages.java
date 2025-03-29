package ui;

public class ErrorMessages {
    public static final String INVALID_COMMAND = "Oh no! Invalid command. Use: todo, deadline, event, list, mark {num}, or unmark {num}.";
    public static final String INVALID_TODO = "Oh no! empty description. Use: todo {task description}";
    public static final String INVALID_DEADLINE = "Oh no! empty description. Use: deadline {task} /by {date}";
    public static final String INVALID_EVENT = "Oh no! empty description. Use: event {task} /from {start} /to {end}";
    public static final String INVALID_TASK_NUMBER = "Oh no! Invalid task number.";
    public static final String MKDIR_FAILED = "Oh no! Unable to create directory for saving task.";
    public static final String SAVE_TASK_FAILED = "Oh no! Unable to save task: ";
    public static final String TASK_LOAD_FAILED = "Oh no! Unable to load task: ";
    public static final String TASK_TYPE_UNKNOWN = "Oh no! Unknown task type: ";
    public static final String TASK_PARSING_ERROR = "Oh no! parsing task: ";
}
