package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.Task;
import innkeeper.task.TodoTask;

/**
 * Represents a command to add a todo task.
 */
public class TodoCommand extends Command {
    private TodoTask task;

    @Override
    public CommandOutput execute(TaskList tasks, Storage storage, Ui ui) throws Exception {
        tasks.addTask(task);
        String message = "Got it. I've added this task:\n"
            + task + "\n"
            + "Now you have " + tasks.getTaskCount() + " tasks in the list.";
        ui.printMessage(message);
        CommandOutput output = new CommandOutput(TerminationType.CONTINUE, message);
        return output;
    }

    @Override
    public Command parse(String input) throws Exception {
        String[] tokens = input.split(" ", 2);
        if (tokens.length < 2) {
            throw new Exception("Usage: todo <description>");
        }
        task = new TodoTask(tokens[1]);
        return this;
    }
}
