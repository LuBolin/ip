package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;

/**
 * Represents a command to exit the program.
 */
public class ByeCommand extends Command {
    @Override
    public CommandOutput execute(TaskList tasks, Storage storage, Ui ui) {
        ui.printFarewell();
        CommandOutput output = new CommandOutput(TerminationType.TERMINATE, "Farewell, traveller!");
        storage.writeTasksToFile(tasks);
        return output;
    }

    @Override
    public Command parse(String input) {
        return this;
    }
}
