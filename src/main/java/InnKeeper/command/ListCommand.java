package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.Task;

import java.util.List;

public class ListCommand extends Command {
    @Override
    public TerminationType execute(TaskList tasks, Storage storage, Ui ui) {
        if (tasks.getTaskCount() == 0) {
            ui.printMessage("There are no tasks in the list.");
            return TerminationType.CONTINUE;
        }

        StringBuilder output = new StringBuilder("Here are the tasks in your list:\n");
        List<Task> userTasks = tasks.getTasks();
        for (int i = 0; i < userTasks .size(); i++) {
            output.append((i + 1)).append(". ").append(userTasks.get(i)).append("\n");
        }
        ui.printMessage(output.toString());
        return TerminationType.CONTINUE;
    }

    @Override
    public Command parse(String input) {
        return this;
    }
}
