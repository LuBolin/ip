package innkeeper.task;

/**
 * Represents a task that has to be done.
 */
public class TodoTask extends Task {
    /**
     * Constructor for TodoTask.
     *
     * @param name Name / Description of the task.
     */
    public TodoTask(String name) {
        super(name, TASK_TYPE.TODO);
    }
}
