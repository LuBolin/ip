package innkeeper;

import innkeeper.command.Command;
import innkeeper.command.Command.TerminationType;
import innkeeper.command.CommandOutput;

import java.io.IOException;

/**
 * InnKeeper is a chatbot that helps users to manage their tasks.
 * It contains the main method to run the chatbot.
 */
public class InnKeeper {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;
    private InputParser inputParser;

    /**
     * Constructor for InnKeeper.
     *
     * @param filePath The file path to save the tasks.
     */
    public InnKeeper(String filePath){
        this.ui = new Ui();
        this.tasks = new TaskList();
        this.inputParser = new InputParser();
        this.storage = new Storage(filePath, this.inputParser);
        try {
            storage.readTasksFromFile(this.tasks, this.ui);
        } catch (IOException e) {
            ui.printMessage(e.getMessage());
        }
    }

    public CommandOutput getResponse(String input) {
        try {
            Command c = inputParser.parseUserInput(input);
            CommandOutput cmdOutput = c.execute(tasks, storage, ui);
            return cmdOutput;
        } catch (Exception e) {
            CommandOutput output = new CommandOutput(TerminationType.CONTINUE, e.getMessage());
            ui.printMessage(output.getResponse());
            return output;
        }
    }

    public String getGreetings(boolean withAsciiArt) {
        return ui.getGreetings(withAsciiArt);
    }
}