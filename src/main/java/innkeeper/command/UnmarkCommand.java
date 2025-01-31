package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.Task;

public class UnmarkCommand extends Command {
    private int index;

    @Override
    public TerminationType execute(TaskList tasks, Storage storage, Ui ui) {
        if (index < 0 || tasks.getTaskCount() <= index){
            throw new IndexOutOfBoundsException("There is no task at index " + (index + 1) + ".");
        }
        Task task = tasks.getTask(index);
        task.setDone(false);
        ui.printMessage("Ahh, I see. I've unmarked this task as not done:\n" + tasks.getTask(index));
        return TerminationType.CONTINUE;
    }

    @Override
    public Command parse(String input) throws Exception {
        String[] tokens = input.split(" ");
        if (tokens.length < 2) {
            throw new Exception("Usage: unmark <index>");
        }
        index = Integer.parseInt(tokens[1]) - 1;
        return this;
    }
}
