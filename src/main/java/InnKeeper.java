import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InnKeeper {
    static final String LINE_SEPARATOR = "____________________________________________________________";
    static final int MAX_LIST_SIZE = 100;
    static List<String> UserInputList = new ArrayList<String>(MAX_LIST_SIZE);


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
        String userInput = sc.nextLine();
        while (true){
            switch (userInput) {
                case "list":
                    printList();
                    break;
                case "bye":
                    return;
                default: // Not a special command, add to list
                    try {
                        addToList(userInput);
                    } catch (ListFullException e) {
                        System.out.println(LINE_SEPARATOR);
                        System.out.println(e.getMessage());
                        System.out.println(LINE_SEPARATOR);
                    }
                    break;
            }
            userInput = sc.nextLine();
        }
    }

    static void addToList(String userInput) throws ListFullException{
        if (UserInputList.size() >= MAX_LIST_SIZE) {
            throw new ListFullException("My notebook is full! I can't add any more notes.");
        }
        UserInputList.add(userInput);
        System.out.println(LINE_SEPARATOR);
        System.out.println("added: " + userInput);
        System.out.println(LINE_SEPARATOR);
        System.out.println();
    }

    static void printList() {
        System.out.println(LINE_SEPARATOR);
        for (int i = 0; i < UserInputList.size(); i++) {
            System.out.println((i + 1) + ". " + UserInputList.get(i));
        }
        System.out.println(LINE_SEPARATOR);
        System.out.println();
    }

    public static class ListFullException extends Exception {
        public ListFullException(String message) {
            super(message);
        }
    }
}