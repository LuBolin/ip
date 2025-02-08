package innkeeper;

import java.util.ArrayList;
import java.util.List;

import innkeeper.task.Task;



/**
 * Class in charge of storing tasks.
 */
public class TaskList {
    final int MAX_LIST_SIZE = 100;
    final List<Task> userTasks;

    /**
     * Constructor for TaskList.
     */
    public TaskList() {
        userTasks = new ArrayList<Task>();
    }

    /**
     * Adds a task to the list of tasks.
     *
     * @param newTask The task to be added.
     * @throws ListFullException If the list is full.
     */
    public void addTask(Task newTask) throws ListFullException {
        String output = "";
        if (userTasks.size() >= MAX_LIST_SIZE) {
            String message = "My notebook is full! I can't add any more notes.\n"
                    + "My old brain can only remember up to " + MAX_LIST_SIZE + " tasks.";
            throw new ListFullException(message);
        }
        userTasks.add(newTask);
    }

    /**
     * Deletes a task from the list of tasks.
     *
     * @param index The index of the task to be deleted.
     */
    public void deleteTask(int index) {
        Task removedTask = userTasks.remove(index);
    }


    /**
     * Gets the number of tasks in the list.
     *
     * @return The number of tasks in the list.
     */
    public int getTaskCount() {
        return userTasks.size();
    }

    /**
     * Gets a task from the list of tasks.
     *
     * @param index The index of the task to get.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return userTasks.get(index);
    }

    /**
     * Gets all the tasks in the list.
     *
     * @return A list of all the tasks.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(userTasks);
    }

    /**
     * Custom exception for when the list is full.
     */
    public static class ListFullException extends Exception {
        public ListFullException(String message) {
            super(message);
        }
    }
}
