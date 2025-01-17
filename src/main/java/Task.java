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
}