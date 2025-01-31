package InnKeeper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task {
    private String deadlineString;
    private LocalDateTime deadlineLocalDate;

    public DeadlineTask(String name, String deadline) {
        super(name, Task.TASK_TYPE.DEADLINE);
        this.deadlineString = deadline;
        try {
            this.deadlineLocalDate = LocalDateTime.parse(deadline);
        } catch (DateTimeParseException e) {
            this.deadlineLocalDate = null;
        }
    }

    @Override
    public String toString() {
        String defaultString = super.toString();
        String deadlineFormattedString;
        if (deadlineLocalDate != null) {
            deadlineFormattedString = "(by: " + deadlineLocalDate.format(Task.OUTPUT_DATE_FORMATTER) + ")";
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
