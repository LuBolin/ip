package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.Task;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private int index;

    @Override
    public CommandOutput execute(TaskList tasks, Storage storage, Ui ui) {
        if (index < 0 || tasks.getTaskCount() <= index){
            throw new IndexOutOfBoundsException("There is no task at index " + (index + 1) + ".");
        }
        Task task = tasks.getTask(index);
        task.setDone(true);
        String message = "Nice! I've marked this task as done:\n" + tasks.getTask(index);
        ui.printMessage(message);
        CommandOutput output = new CommandOutput(TerminationType.CONTINUE, message);
        return output;
    }

    @Override
    public Command parse(String input) throws Exception {
        String[] tokens = input.split(" ");
        if (tokens.length < 2) {
            throw new Exception("Usage: mark <index>");
        }
        index = Integer.parseInt(tokens[1]) - 1;
        return this;
    }
}
