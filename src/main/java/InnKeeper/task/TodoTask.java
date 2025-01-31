package innkeeper.task;

public class TodoTask extends Task {
    public TodoTask(String name) {
        super(name, TASK_TYPE.TODO);
    }
}
