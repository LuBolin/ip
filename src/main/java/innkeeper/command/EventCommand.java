package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.DeadlineTask;
import innkeeper.task.EventTask;
import innkeeper.task.Task;

public class EventCommand extends Command {
    private EventTask task;

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
            if (tokens[1].contains(" /from ")) {
                String[] front = tokens[1].split(" /from ");
                if (front.length == 2) {
                    String[] back = front[1].split(" /to ");
                    if (back.length == 2) {
                        // description, start, end
                        task = new EventTask(front[0], back[0], back[1]);
                        return this;
                    }
                }
            }
        }

        String exceptionMessage = "Usage: event <description> /from <start> /to <end>\n";
        exceptionMessage += "Input dates in " + Task.INPUT_DATE_FORMAT + " to be parsed for better display.";
        throw new Exception(exceptionMessage);
    }
}
