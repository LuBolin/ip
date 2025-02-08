package innkeeper.command;

import java.util.List;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.Task;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    @Override
    public CommandOutput execute(TaskList tasks, Storage storage, Ui ui) {
        if (tasks.getTaskCount() == 0) {
            String message = "There are no tasks in the list.";
            ui.printMessage(message);
            CommandOutput output = new CommandOutput(TerminationType.CONTINUE, message);
            return output;
        }

        StringBuilder message = new StringBuilder("Here are the tasks in your list:\n");
        List<Task> userTasks = tasks.getTasks();
        for (int i = 0; i < userTasks.size(); i++) {
            message.append((i + 1)).append(". ").append(userTasks.get(i)).append("\n");
        }
        ui.printMessage(message.toString());
        CommandOutput output = new CommandOutput(TerminationType.CONTINUE, message.toString());
        return output;
    }

    @Override
    public Command parse(String input) {
        return this;
    }
}
