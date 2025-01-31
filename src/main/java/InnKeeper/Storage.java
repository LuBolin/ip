package innkeeper;

import innkeeper.command.Command;
import innkeeper.command.MarkCommand;
import innkeeper.command.TodoCommand;
import innkeeper.task.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String FILE_PATH;
    private final InputParser inputParser;

    public Storage(String filePath, InputParser inputParser){
        this.FILE_PATH = filePath;
        this.inputParser = inputParser;
    }

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
                    if (success){
                        success = file.createNewFile();
                    }
                }
                if (!success) {
                    System.out.println("Failed to write to file " + FILE_PATH);
                    System.out.println("File not found and could not creat it.");
                    return;
                }
            }
            PrintWriter writer = new PrintWriter(FILE_PATH);
            for (Task task : userTasks.getTasks()) {
                writer.println(task.toFileString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }

    public List<Task> readTasksFromFile(TaskList taskList, Storage storage, Ui ui) throws IOException{
        // check if file exists, if not just skip
        List<Task> tasks = new ArrayList<Task>();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks;
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
            default -> {continue;}
            }
            try {
                Command newCommand = inputParser.parseUserInput(userInput);
                // check if newCommand is a TaskCommand
                if (newCommand instanceof TodoCommand
                        || newCommand instanceof innkeeper.command.DeadlineCommand
                        || newCommand instanceof innkeeper.command.EventCommand) {
                    try{
                        newCommand.execute(taskList, storage, ui);
                        if (parts[1].equals("1")) {
                            int index = taskList.getTasks().size() - 1;
                            MarkCommand markCommand = new MarkCommand();
                            markCommand = (MarkCommand) markCommand.parse("mark " + (index + 1));
                            markCommand.execute(taskList, storage, ui);
                        }
                    } catch (Exception e) {
                        // Ignore the task if it cannot be added
                    }
                }
            } catch (Exception e) {
                throw new IOException("Un-parse-able task found in save file.");
            }
        }

        return tasks;
    }
}
