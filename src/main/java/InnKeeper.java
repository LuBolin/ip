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

    static void printGreetings() {
        // Generated with https://patorjk.com/software/taag/
        // Font: "Big" (The font is called "Big" on the website)
        // Name: InnKeeper
        String logo = " _____             _  __\n"
                + "|_   _|           | |/ /                         \n"
                + "  | |  _ __  _ __ | ' / ___  ___ _ __   ___ _ __ \n"
                + "  | | | '_ \\| '_ \\|  < / _ \\/ _ \\ '_ \\ / _ \\ '__|\n"
                + " _| |_| | | | | | | . \\  __/  __/ |_) |  __/ |   \n"
                + "|_____|_| |_|_| |_|_|\\_\\___|\\___| .__/ \\___|_|   \n"
                + "                                | |              \n"
                + "                                |_|              \n";
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

    static void handleUserInput() {
        Scanner sc = new Scanner(System.in);
        String userInput;

        String markAndUnmarkRegex = "(mark|unmark) \\d+";
        while (true) {
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
            } else {
                try {
                    addToList(userInput);
                } catch (ListFullException e) {
                    System.out.println(LINE_SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(LINE_SEPARATOR);
                    System.out.println();
                }
            }
        }
    }

    static void addToList(String userInput) throws ListFullException{
        if (UserTaskList.size() >= MAX_LIST_SIZE) {
            throw new ListFullException("My notebook is full! I can't add any more notes.");
        }
        Task newTask = new Task(userInput);
        UserTaskList.add(newTask);
        System.out.println(LINE_SEPARATOR);
        System.out.println("added: " + userInput);
        System.out.println(LINE_SEPARATOR);
        System.out.println();
    }

    static void printList() {
        System.out.println(LINE_SEPARATOR);
        for (int i = 0; i < UserTaskList.size(); i++) {
            System.out.println((i + 1) + ". " + UserTaskList.get(i));
        }
        System.out.println(LINE_SEPARATOR);
        System.out.println();
    }

    static void setDone(int index, boolean isDone) throws IndexOutOfBoundsException {
        if (index < 0 || index >= UserTaskList.size()) {
            throw new IndexOutOfBoundsException("No is task of index " + index);
        }
        Task task = UserTaskList.get(index);
        task.setDone(isDone);
    }

    public static class ListFullException extends Exception {
        public ListFullException(String message) {
            super(message);
        }
    }
}