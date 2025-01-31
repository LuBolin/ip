package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.Task;

public class TodoCommand extends Command {
    private Task task;

    @Override
    public TerminationType execute(TaskList tasks, Storage storage, Ui ui) throws Exception {
        tasks.addTask(task);
        String message = "Got it. I've added this task:\n"
            + task + "\n"
            + "Now you have " + tasks.getTaskCount() + " tasks in the list.";
        ui.printMessage(message);
        return TerminationType.CONTINUE;
    }

    @Override
    public Command parse(String input) throws Exception {
        String[] tokens = input.split(" ", 2);
        if (tokens.length < 2) {
            throw new Exception("Usage: todo <description>");
        }
        task = new Task(tokens[1]);
        return this;
    }
}
