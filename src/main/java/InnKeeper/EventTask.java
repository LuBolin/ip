package InnKeeper;

public class EventTask extends Task {
    private String startDatetime;
    private String endDatetime;

    public EventTask(String name, String startDatetime, String endDatetime) {
        super(name, Task.TASK_TYPE.EVENT);
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
    }

    @Override
    public String toString() {
        String defaultString = super.toString();
        String datetimeString = "(from: " + startDatetime + " to " + endDatetime + ")";
        return defaultString + " " + datetimeString;
    }

    @Override
    public String toFileString() {
        return super.toFileString(new String[] {startDatetime, endDatetime});
    }
}
