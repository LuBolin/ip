package innkeeper.command;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;

/**
 * Abstract class for all commands.
 * Represents a command that can be executed by the user.
 */

public abstract class Command {
    public enum TerminationType {
        TERMINATE,
        CONTINUE
    }

    /**
     * Executes the command
     *
     * @param tasks The list of tasks
     * @param ui The ui object
     * @param storage The storage object
     * @return TerminationType Whether the command is a termination command
     * @throws Exception If an error occurs during execution
     */
    abstract public CommandOutput execute(TaskList tasks, Storage storage, Ui ui) throws Exception;

    /**
     * Parses the input string and returns the corresponding Command object
     *
     * @param input The input string
     * @return Command A Command object
     * @throws Exception If the input string is invalid
     */
    abstract public Command parse(String input) throws Exception;
}
