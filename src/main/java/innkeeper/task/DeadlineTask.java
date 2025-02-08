package innkeeper.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that has a deadline.
 */
public class DeadlineTask extends Task {
    private String deadlineString;
    private LocalDate deadlineLocalDate;

    /**
     * Constructor for DeadlineTask.
     *
     * @param name Name / Description of the task.
     * @param deadline Deadline of the task.
     */
    public DeadlineTask(String name, String deadline) {
        super(name, Task.TASK_TYPE.DEADLINE);
        this.deadlineString = deadline;
        try {
            this.deadlineLocalDate = LocalDate.parse(deadline, getInputDateParser());
        } catch (DateTimeParseException e) {
            this.deadlineLocalDate = null;
        }
    }

    @Override
    public String toString() {
        String defaultString = super.toString();
        String deadlineFormattedString;
        if (deadlineLocalDate != null) {
            deadlineFormattedString = "(by: " + deadlineLocalDate.format(getOutputDateFormatter()) + ")";
        } else {
            deadlineFormattedString = "(by: " + deadlineString + ")";
        }
        return defaultString + " " + deadlineFormattedString;
    }

    @Override
    public String toFileString() {
        String[] informationArray = new String[1];
        if (deadlineLocalDate != null) {
            informationArray[0] = deadlineLocalDate.toString();
        } else {
            informationArray[0] = deadlineString;
        }
        return super.toFileString(informationArray);
    }
}
