package innkeeper.task;

import java.time.LocalDate;

/**
 * Represents a task that is an event.
 */
public class EventTask extends Task {
    private String startDateString;
    private String endDateString;
    private LocalDate startDateLocalDate;
    private LocalDate endDateLocalDate;

    /**
     * Constructor for EventTask.
     *
     * @param name Name / Description of the task.
     * @param startDatetime Start date and time of the event.
     * @param endDatetime End date and time of the event.
     */
    public EventTask(String name, String startDatetime, String endDatetime) {
        super(name, Task.TASK_TYPE.EVENT);
        this.startDateString = startDatetime;
        this.endDateString = endDatetime;
        try {
            this.startDateLocalDate = LocalDate.parse(startDatetime, getInputDateParser());
        } catch (Exception e) {
            this.startDateLocalDate = null;
        }
        try {
            this.endDateLocalDate = LocalDate.parse(endDatetime, getInputDateParser());
        } catch (Exception e) {
            this.endDateLocalDate = null;
        }
    }

    @Override
    public String toString() {
        String defaultString = super.toString();
        String startDateFormattedString;
        String endDateFormattedString;
        if (startDateLocalDate != null) {
            startDateFormattedString = startDateLocalDate.format(getOutputDateFormatter());
        } else {
            startDateFormattedString = startDateString;
        }
        if (endDateLocalDate != null) {
            endDateFormattedString = endDateLocalDate.format(getOutputDateFormatter());
        } else {
            endDateFormattedString = endDateString;
        }
        String datetimeString = "(from: " + startDateFormattedString + " to: " + endDateFormattedString + ")";
        return defaultString + " " + datetimeString;
    }

    @Override
    public String toFileString() {
        String[] informationArray = new String[2];
        if (startDateLocalDate != null) {
            informationArray[0] = startDateLocalDate.toString();
        } else {
            informationArray[0] = startDateString;
        }
        if (endDateLocalDate != null) {
            informationArray[1] = endDateLocalDate.toString();
        } else {
            informationArray[1] = endDateString;
        }
        return super.toFileString(informationArray);
    }
}
