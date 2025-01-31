package innkeeper.task;

import java.time.format.DateTimeFormatter;

/**
 * Abstract class for tasks.
 */
public abstract class Task {
    public enum TASK_TYPE {
        TODO, DEADLINE, EVENT
    }

    // MM is month, mm is minute
    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
    static final DateTimeFormatter OUTPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    static final DateTimeFormatter INPUT_DATE_PARSER = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);

    String name;
    boolean isDone;
    TASK_TYPE type;

    /**
     * Constructor for Task.
     *
     * @param name The name / description of the task.
     * @param type The type of the task.
     */
    public Task(String name, TASK_TYPE type) {
        this.name = name;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Sets if the task is done.
     *
     * @param isDone The boolean value to set if the task is done.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Formats the task as a string of the desired format.
     *
     * @return The formatted string.
     */
    @Override
    public String toString() {
        String typeString = "[" + type.name().charAt(0) + "]";
        String doneString = "[" + (isDone ? "X" : " ") + "]";
        String nameString = name;
        return typeString + doneString + " " + nameString;
    }

    /**
     * Formats the task as a string to be written to a file.
     *
     * @return The formatted string.
     */
    public String toFileString(String[] information) {
        String typeString = (type.name().charAt(0) + "").toUpperCase();
        String doneString = isDone ? "1" : "0";
        String nameString = name;
        StringBuilder sb = new StringBuilder();
        sb.append(typeString).append(" | ").append(doneString).append(" | ").append(nameString);
        for (String info : information) {
            sb.append(" | ").append(info);
        }
        return sb.toString();
    }

    /**
     * Formats the task as a string to be written to a file.
     * Used when there is no additional parameters.
     * Overloads the toFileString(String[] information) method.
     *
     * @return The formatted string.
     */
    public String toFileString() {
        return toFileString(new String[0]);
    }
}