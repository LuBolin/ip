package innkeeper;

import innkeeper.command.Command;
import innkeeper.command.Command.TerminationType;

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
    }

    /**
     * Main method to run the chatbot.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new InnKeeper("data/tasks.txt").run();
    }

    /**
     * Runs the chatbot.
     */
    public void run(){
        try{
            storage.readTasksFromFile(tasks, storage, ui);
        } catch (IOException e) {
            ui.printMessage("Error when loading saved tasks: " + e.getMessage());
        }
        ui.setInitialized();
        
        ui.printGreetings();

        boolean isExit = false;
        while (!isExit){
            try {
                String fullCommand = ui.readCommand();
                ui.printLine(); // show the divider line ("_______")
                Command c = inputParser.parseUserInput(fullCommand);
                TerminationType termination = c.execute(tasks, storage, ui);
                isExit = (termination == TerminationType.TERMINATE);
            } catch (Exception e) {
                ui.printMessage(e.getMessage());
            } finally {
                ui.printLine();
            }
        }
    }
}