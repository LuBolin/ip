package innkeeper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import innkeeper.command.Command;
import innkeeper.command.MarkCommand;
import innkeeper.command.TodoCommand;
import innkeeper.task.Task;

/**
 * Class in charge of reading and writing tasks to a file.
 */
public class Storage {
    private final String FILE_PATH;
    private final InputParser inputParser;

    /**
     * Constructor for Storage.
     *
     * @param filePath The file path to save the tasks.
     * @param inputParser The input parser.
     */
    public Storage(String filePath, InputParser inputParser) {
        this.FILE_PATH = filePath;
        this.inputParser = inputParser;
    }

    /**
     * Writes the tasks to a file.
     *
     * @param userTasks The TaskList object containing  the list of tasks to write to the file.
     */
    public void writeTasksToFile(TaskList userTasks) {
        // Type | isDone | Description | Other fields
        // check if file exists, if not create it
        // then, write to file
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                File parentDir = file.getParentFile();
                boolean success = true;
                if (!parentDir.exists()) {
                    success = parentDir.mkdirs();
                    if (success) {
                        success = file.createNewFile();
                    }
                }
                if (!success) {
                    System.out.println("Failed to write to file " + FILE_PATH);
                    System.out.println("File not found and could not creat it.");
                    return;
                }
            }

            assert file.exists() : "File should exist at this point";

            PrintWriter writer = new PrintWriter(FILE_PATH);
            for (Task task : userTasks.getTasks()) {
                writer.println(task.toFileString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }

    /**
     * Reads the tasks from a file.
     *
     * @param taskList The TaskList object to store the tasks.
     * @param ui The Ui object to interact with the user.
     * @throws IOException If there is an error reading the file.
     */
    public void readTasksFromFile(TaskList taskList, Ui ui) throws IOException {
        // check if file exists, if not just skip
        List<Task> tasks = new ArrayList<Task>();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" \\| ");

            // Type | isDone | Description | Other fields
            String userInput = "";
            switch (parts[0]) {
            case "T" -> userInput = "todo " + parts[2];
            case "D" -> userInput = "deadline " + parts[2] + " /by " + parts[3];
            case "E" -> userInput = "event " + parts[2] + " /from " + parts[3] + " /to " + parts[4];
            default -> {
                continue; }
            }
            try {
                Command newCommand = inputParser.parseUserInput(userInput);
                // check if newCommand is a TaskCommand
                if (newCommand instanceof TodoCommand
                        || newCommand instanceof innkeeper.command.DeadlineCommand
                        || newCommand instanceof innkeeper.command.EventCommand) {
                    try {
                        newCommand.execute(taskList, this, ui);
                        if (parts[1].equals("1")) {
                            int index = taskList.getTasks().size() - 1;
                            MarkCommand markCommand = new MarkCommand();
                            markCommand = (MarkCommand) markCommand.parse("mark " + (index + 1));
                            markCommand.execute(taskList, this, ui);
                        }
                    } catch (Exception e) {
                        // Ignore the task if it cannot be added
                    }
                }
            } catch (Exception e) {
                throw new IOException("Un-parse-able task found in save file.");
            }
        }

    }
}
