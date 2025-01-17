import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InnKeeper {
    static final String LINE_SEPARATOR = "____________________________________________________________";
    static final int MAX_LIST_SIZE = 100;
    static List<Task> UserTaskList = new ArrayList<>(MAX_LIST_SIZE);

    public static void main(String[] args) {
        printGreetings();

        handleUserInput();

        printFarewell();
    }


    // User Input Handling
    static void handleUserInput() {
        Scanner sc = new Scanner(System.in);
        String userInput;

        String markAndUnmarkRegex = "(mark|unmark) \\d+";
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
                    System.out.println(UserTaskList.get(index));
                    System.out.println(LINE_SEPARATOR);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(LINE_SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(LINE_SEPARATOR);
                    System.out.println();
                }
            } else { // Add a new task
                try {
                    Task newTask = parseInputAsTask(userInput);
                    addToList(newTask);
                } catch (IllegalArgumentException | ListFullException e) {
                    System.out.println(LINE_SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(LINE_SEPARATOR);
                    System.out.println();
                }
            }
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
            String exceptionMessage = """
                    I'm sorry, but I don't know what that means.\n
                    Task types: todo, deadline, event.\n
                    Other commands: list, mark X, unmark X (X is a number).
                    Or type "bye" to exit.""";
            throw new IllegalArgumentException(exceptionMessage);
        }
        return newTask;
    }

    static void addToList(Task newTask) throws ListFullException{
        if (UserTaskList.size() >= MAX_LIST_SIZE) {
            throw new ListFullException("My notebook is full! I can't add any more notes.");
        }
        UserTaskList.add(newTask);
        System.out.println(LINE_SEPARATOR);
        System.out.println("added: " + newTask);
        System.out.println(LINE_SEPARATOR);
        System.out.println();
    }

    static void printList() {
        System.out.println(LINE_SEPARATOR);
        if (UserTaskList.isEmpty()) {
            System.out.println("You have no tasks in your list.");
        } else {
            for (int i = 0; i < UserTaskList.size(); i++) {
                System.out.println((i + 1) + ". " + UserTaskList.get(i));
            }
        }
        System.out.println(LINE_SEPARATOR);
        System.out.println();
    }

    static void setDone(int index, boolean isDone) throws IndexOutOfBoundsException {
        if (index < 0 || index >= UserTaskList.size()) {
            throw new IndexOutOfBoundsException("There is no task at index " + (index + 1) + ".");
        }
        Task task = UserTaskList.get(index);
        task.setDone(isDone);
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