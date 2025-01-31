package InnKeeper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InnKeeper {
    static final String LINE_SEPARATOR = "____________________________________________________________";
    static final int MAX_LIST_SIZE = 100;
    static final String STORED_TASKS_FILE_PATH = "data/innkeeper.txt"; // Hardcoded Unix Path
    static List<Task> userTasks  = new ArrayList<>(MAX_LIST_SIZE);
    static boolean isActive = false;

    public static void main(String[] args) {
        readFromFile();

        printGreetings();

        isActive = true;

        handleUserInput();

        printFarewell();

        writeToFile();
    }


    // User Input Handling
    static void handleUserInput() {
        Scanner sc = new Scanner(System.in);
        String userInput;

        String markAndUnmarkRegex = "(mark|unmark) \\d+";
        String deleteRegex = "delete \\d+";
        while (true) {
            if (!sc.hasNextLine()) { // EOF or input stream closed
                break;
            }
            userInput = sc.nextLine();
            // cannot simply use switch case here, due to commands like "'"mark X"
            if (userInput.equals("bye")) {
                break;
            } else if (userInput.equals("list")) {
                printList();
            } else if (userInput.matches(markAndUnmarkRegex)) {
                boolean isMark = userInput.startsWith("mark");
                try {
                    int index = Integer.parseInt(userInput.split(" ")[1]);
                    index -= 1;
                    setDone(index, isMark);

                    System.out.println(LINE_SEPARATOR);
                    if (isMark) {
                        System.out.println("Good job! It's marked as done:");
                    } else {
                        System.out.println("Ahh, I see. It's no longer marked as done:");
                    }
                    System.out.println(userTasks .get(index));
                    System.out.println(LINE_SEPARATOR);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(LINE_SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(LINE_SEPARATOR);
                }
            } else if (userInput.matches(deleteRegex)) {
                try {
                    int index = Integer.parseInt(userInput.split(" ")[1]);
                    index -= 1;
                    deleteTask(index);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(LINE_SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(LINE_SEPARATOR);
                }
            }
            else { // Add a new task
                try {
                    Task newTask = parseInputAsTask(userInput);
                    addToList(newTask);
                } catch (IllegalArgumentException | ListFullException e) {
                    System.out.println(LINE_SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(LINE_SEPARATOR);
                }
            }
            System.out.println();
        }
    }

    private static Task parseInputAsTask(String userInput) throws IllegalArgumentException {
        Task newTask;
        if (userInput.startsWith("todo")) {
            int substringStart = 5; // length of command
            String exceptionMessage = "A todo task must have a description.\n"
                    + "Format: \"todo description\".";
            if (userInput.length() <= substringStart) {
                throw new IllegalArgumentException(exceptionMessage);
            }
            String description = userInput.substring(substringStart);
            if (description.isBlank()) {
                throw new IllegalArgumentException(exceptionMessage);
            }
            newTask = new TodoTask(description);
        } else if (userInput.startsWith("deadline")) {
            int substringStart = 9; // length of command
            String exceptionMessage = "A deadline task must have a description and a deadline datetime.\n"
                    + "Format: \"deadline description \"/by deadline\".";
            exceptionMessage += "\nInput dates in " + Task.INPUT_DATE_FORMAT + " to be parsed for better display.";
            if (userInput.length() < substringStart) {
                throw new IllegalArgumentException(exceptionMessage);
            }
            String[] parts = userInput.substring(substringStart).split(" /by ");
            if (parts.length != 2) {
                throw new IllegalArgumentException(exceptionMessage);
            }
            newTask = new DeadlineTask(parts[0], parts[1]);
        } else if (userInput.startsWith("event")) {
            int substringStart = 6; // length of command
            String exceptionMessage = "An event task must have a description, a start and an end datetime.\n"
                    + "Format: \"event description /from start /to end\".";
            exceptionMessage += "\nInput dates in " + Task.INPUT_DATE_FORMAT + " to be parsed for better display.";
            if (userInput.length() < substringStart) {
                throw new IllegalArgumentException(exceptionMessage);
            }
            String[] parts = userInput.substring(substringStart).split(" /from ");
            if (parts.length != 2) {
                throw new IllegalArgumentException(exceptionMessage);
            }
            String[] parts2 = parts[1].split(" /to ");
            if (parts2.length != 2) {
                throw new IllegalArgumentException(exceptionMessage);
            }
            newTask = new EventTask(parts[0], parts2[0], parts2[1]);
        } else { // Invalid commands
            String inputDateFormat = Task.INPUT_DATE_FORMAT;
            String exceptionMessage = String.format("""
                I'm sorry, but I don't know what that means.
                
                Task types: todo, deadline, event.
                Input dates in %s to be parsed for better display.
                
                Other commands: list, mark X, unmark X, delete X.
                (X is the index of the task in the list).
                
                If you are leaving, just say "bye."
                """, inputDateFormat);
            System.out.println(exceptionMessage);

            throw new IllegalArgumentException(exceptionMessage);
        }
        return newTask;
    }

    static void addToList(Task newTask) throws ListFullException{
        if (userTasks .size() >= MAX_LIST_SIZE) {
            String exceptionMessage = "My notebook is full! I can't add any more notes.\n" +
                    "My old brain can only remember up to " + MAX_LIST_SIZE + " tasks.";
            throw new ListFullException(exceptionMessage);
        }
        userTasks.add(newTask);
        if (! isActive){
            return;
        }
        System.out.println(LINE_SEPARATOR);
        System.out.println("Got it! Adding task: " + newTask);
        System.out.println("Now you have " + userTasks .size() + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);
    }

    static void printList() {
        System.out.println(LINE_SEPARATOR);
        if (userTasks .isEmpty()) {
            System.out.println("You have no tasks in your list.");
        } else {
            for (int i = 0; i < userTasks .size(); i++) {
                System.out.println((i + 1) + ". " + userTasks .get(i));
            }
        }
        System.out.println(LINE_SEPARATOR);
    }

    static void setDone(int index, boolean isDone) throws IndexOutOfBoundsException {
        if (index < 0 || index >= userTasks .size()) {
            throw new IndexOutOfBoundsException("There is no task at index " + (index + 1) + ".");
        }
        Task task = userTasks .get(index);
        task.setDone(isDone);
    }

    static void deleteTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= userTasks .size()) {
            throw new IndexOutOfBoundsException("There is no task at index " + (index + 1) + ".");
        }
        Task removedTask = userTasks .remove(index);
        System.out.println(LINE_SEPARATOR);
        System.out.println("Noted. I've removed this task:");
        System.out.println(removedTask);
        System.out.println("Now you have " + userTasks .size() + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);
    }

    // File IO
    static void readFromFile() {
        // check if file exists, if not just skip
        try {
            java.io.File file = new java.io.File(STORED_TASKS_FILE_PATH);
            if (!file.exists()) {
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(STORED_TASKS_FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                // Type | isDone | Description | Other fields
                String userInput;
                switch (parts[0]) {
                case "T" -> userInput = "todo " + parts[2];
                case "D" -> userInput = "deadline " + parts[2] + " /by " + parts[3];
                case "E" -> userInput = "event " + parts[2] + " /from " + parts[3] + " /to " + parts[4];
                default -> {continue;}
                }
                Task newTask = parseInputAsTask(userInput);
                if (parts[1].equals("1")) {
                    newTask.setDone(true);
                }
                addToList(newTask);
            }
        } catch (ListFullException e) {
            System.out.println("List is full when reading saved file.\n" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    static void writeToFile(){
        // Type | isDone | Description | Other fields
        // check if file exists, if not create it
        // then, write to file
        try {
            java.io.File file = new java.io.File(STORED_TASKS_FILE_PATH);
            if (!file.exists()) {
                boolean success = file.getParentFile().mkdirs();
                if (success){
                    success = file.createNewFile();
                }
                if (!success) {
                    System.out.println("Failed to write to file " + STORED_TASKS_FILE_PATH);
                    System.out.println("File not found and could not creat it.");
                    return;
                }
            }
            java.io.PrintWriter writer = new java.io.PrintWriter(STORED_TASKS_FILE_PATH);
            for (Task task : userTasks) {
                writer.println(task.toFileString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }



    // Fixed Messages
    static void printGreetings() {
        // Generated with https://patorjk.com/software/taag/
        // Font: "Big" (The font is called "Big" on the website)
        // Name: InnKeeper
        String logo = """
                 _____             _  __
                |_   _|           | |/ /                        \s
                  | |  _ __  _ __ | ' / ___  ___ _ __   ___ _ __\s
                  | | | '_ \\| '_ \\|  < / _ \\/ _ \\ '_ \\ / _ \\ '__|
                 _| |_| | | | | | | . \\  __/  __/ |_) |  __/ |  \s
                |_____|_| |_|_| |_|_|\\_\\___|\\___| .__/ \\___|_|  \s
                                                | |             \s
                                                |_|             \s
                """;
        System.out.println("Hello from\n" + logo);

        System.out.println(LINE_SEPARATOR);
        System.out.println("Greetings! I'm the InnKeeper.");
        System.out.println("I can keep track of things for you.");
        System.out.println(LINE_SEPARATOR);
        // Print an empty line for better readability of user input
        System.out.println();
    }

    static void printFarewell() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Farewell, traveller!");
        System.out.println(LINE_SEPARATOR);
    }

    // Custom Exceptions
    public static class ListFullException extends Exception {
        public ListFullException(String message) {
            super(message);
        }
    }
}