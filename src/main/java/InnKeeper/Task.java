package InnKeeper;

public class Task {
    public enum TASK_TYPE {
        TODO, DEADLINE, EVENT
    }

    String name;
    boolean isDone;
    TASK_TYPE type;

    public Task(String name, TASK_TYPE type) {
        this.name = name;
        this.isDone = false;
        this.type = type;
    }

    public Task(String name) {
        this(name, TASK_TYPE.TODO);
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        String typeString = "[" + type.name().charAt(0) + "]";
        String doneString = "[" + (isDone ? "X" : " ") + "]";
        String nameString = name;
        return typeString + doneString + " " + nameString;
    }

    public String toFileString() {
        return toFileString(new String[0]);
    }

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
}