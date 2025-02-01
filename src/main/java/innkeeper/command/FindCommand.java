package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.Task;

import java.util.List;

/**
 * Represents a command to find tasks that contain a keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    @Override
    public CommandOutput execute(TaskList tasks, Storage storage, Ui ui) {
        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:\n");
        List<Task> userTasks = tasks.getTasks();
        boolean found = false;
        for (int i = 0; i < userTasks .size(); i++) {
            if (userTasks.get(i).toString().contains(keyword)) {
                found = true;
                message.append((i + 1)).append(". ").append(userTasks.get(i)).append("\n");
            }
        }
        if (!found) {
            message = new StringBuilder("There are no tasks in the list that match the keyword.");
        }
        ui.printMessage(message.toString());
        CommandOutput output = new CommandOutput(TerminationType.CONTINUE, message.toString());
        return output;
    }

    @Override
    public Command parse(String input) throws Exception {
        String[] tokens = input.split(" ", 2);
        if (tokens.length < 2) {
            throw new Exception("Usage: find <keyword>");
        }
        keyword = tokens[1];
        return this;
    }
}
