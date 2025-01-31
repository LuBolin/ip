package innkeeper;

import innkeeper.command.Command;
import innkeeper.command.Command.TerminationType;

import java.io.IOException;

public class InnKeeper {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;
    private InputParser inputParser;

    public InnKeeper(String filePath){
        this.ui = new Ui();
        this.tasks = new TaskList();
        this.inputParser = new InputParser();
        this.storage = new Storage(filePath, this.inputParser);
    }

    public static void main(String[] args) {
        new InnKeeper("data/tasks.txt").run();
    }

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

        // print farewell is handled in ByeCommand

    }
}