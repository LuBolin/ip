package InnKeeper;

public class DeadlineTask extends Task {
    private String deadline;

    public DeadlineTask(String name, String deadline) {
        super(name, Task.TASK_TYPE.DEADLINE);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        String defaultString = super.toString();
        String deadlineString = "(by: " + deadline + ")";
        return defaultString + " " + deadlineString;
    }

    @Override
    public String toFileString() {
        return super.toFileString(new String[] {deadline});
    }
}
