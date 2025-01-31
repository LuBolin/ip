package innkeeper.task;

import java.time.LocalDate;

public class EventTask extends Task {
    private String startDateString;
    private String endDateString;
    private LocalDate startDateLocalDate;
    private LocalDate endDateLocalDate;

    public EventTask(String name, String startDatetime, String endDatetime) {
        super(name, Task.TASK_TYPE.EVENT);
        this.startDateString = startDatetime;
        this.endDateString = endDatetime;
        try {
            this.startDateLocalDate = LocalDate.parse(startDatetime, INPUT_DATE_PARSER);
        } catch (Exception e) {
            this.startDateLocalDate = null;
        }
        try {
            this.endDateLocalDate = LocalDate.parse(endDatetime, INPUT_DATE_PARSER);
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
            startDateFormattedString = startDateLocalDate.format(Task.OUTPUT_DATE_FORMATTER);
        } else {
            startDateFormattedString = startDateString;
        }
        if (endDateLocalDate != null) {
            endDateFormattedString = endDateLocalDate.format(Task.OUTPUT_DATE_FORMATTER);
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
