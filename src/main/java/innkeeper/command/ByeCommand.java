package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;

/**
 * Represents a command to exit the program.
 */
public class ByeCommand extends Command {
    @Override
    public TerminationType execute(TaskList tasks, Storage storage, Ui ui) {
        ui.printFarewell();
        storage.writeTasksToFile(tasks);
        return TerminationType.TERMINATE;
    }

    @Override
    public Command parse(String input) {
        return this;
    }
}
