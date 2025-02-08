package innkeeper.command;

import innkeeper.command.Command.TerminationType;

/**
 * Represents the output of a command.
 */
public class CommandOutput {
    private final TerminationType termination;
    private final String response;

    /**
     * Constructor for CommandOutput.
     *
     * @param termination The termination type of the command.
     * @param response The response of the command.
     */
    public CommandOutput(TerminationType termination, String response) {
        this.termination = termination;
        this.response = response;
    }

    public TerminationType getTermination() {
        return termination;
    }

    public String getResponse() {
        return response;
    }
}
