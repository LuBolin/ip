package innkeeper;

import innkeeper.task.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    final int MAX_LIST_SIZE = 100;
    final List<Task> userTasks;

    public TaskList() {
        userTasks = new ArrayList<Task>();
    }

    public void addTask(Task newTask) throws ListFullException {
        String output = "";
        if (userTasks.size() >= MAX_LIST_SIZE) {
            String message = "My notebook is full! I can't add any more notes.\n" +
                    "My old brain can only remember up to " + MAX_LIST_SIZE + " tasks.";
            throw new ListFullException(message);
        }
        userTasks.add(newTask);
    }

    public void deleteTask(int index){
        Task removedTask = userTasks.remove(index);
    }

    public int getTaskCount() {
        return userTasks.size();
    }

    public Task getTask(int index) {
        return userTasks.get(index);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(userTasks);
    }

    // Custom Exceptions
    public static class ListFullException extends Exception {
        public ListFullException(String message) {
            super(message);
        }
    }
}