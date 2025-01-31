package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.Task;

public class DeleteCommand extends Command {
    private int index;

    @Override
    public TerminationType execute(TaskList tasks, Storage storage, Ui ui) throws Exception {
        if (index < 0 || tasks.getTaskCount() <= index){
            throw new IndexOutOfBoundsException("There are only " + tasks.getTaskCount() + " tasks in the list.");
        }
        Task taskToDelete = tasks.getTask(index);
        tasks.deleteTask(index);
        String message = "Noted. I've removed this task:\n"
            + taskToDelete + "\n"
            + "Now you have " + tasks.getTaskCount() + " tasks in the list.";
        ui.printMessage(message);
        return TerminationType.CONTINUE;
    }

    @Override
    public Command parse(String input) throws Exception {
        String[] tokens = input.split(" ", 2);
        if (tokens.length < 2) {
            throw new Exception("Usage: delete <index>");
        }
        index = Integer.parseInt(tokens[1]) - 1;
        return this;
    }
}
