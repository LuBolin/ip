package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.DeadlineTask;
import innkeeper.task.Task;

public class DeadlineCommand extends Command {
    private DeadlineTask task;

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
        if (tokens.length == 2) {
            if (tokens[1].contains(" /by ")) {
                String[] params = tokens[1].split(" /by ");
                if (params.length == 2) {
                    // description, deadline
                    task = new DeadlineTask(params[0], params[1]);
                    return this;
                }
            }
        }

        String exceptionMessage = "Usage: deadline <description> /by <deadline>\n";
        exceptionMessage += "Input dates in " + Task.INPUT_DATE_FORMAT + " to be parsed for better display.";
        throw new Exception(exceptionMessage);
    }
}
