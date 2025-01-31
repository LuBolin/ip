package innkeeper;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class in charge of handling user interface.
 * It prints messages to the user and reads user input.
 */
public class Ui {
    private final String LINE_SEPARATOR = "____________________________________________________________";
    private boolean isInitialized = false;

    /**
     * Prints the greetings message.
     */
    public void printGreetings() {
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
    }

    /**
     * Prints the farewell message.
     */
    public void printFarewell() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Farewell, traveller!");
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints a line separator.
     */
    public void printLine(){
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints a message to the user.
     *
     * @param message The message to print.
     */
    public void printMessage(String message) {
        if (!isInitialized){
            return;
        }
        System.out.println(LINE_SEPARATOR);
        System.out.println(message);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Reads the user input.
     *
     * @return The user input.
     */
    public String readCommand() {
        Scanner scanner = new Scanner(System.in);
        try{
            return scanner.nextLine();
        } catch (NoSuchElementException | IllegalStateException e){
            return "bye";
        }
    }

    /**
     * Sets the Ui as initialized.
     * This is useful when the Ui is used before the greetings message is printed.
     * Such as when we are loading tasks from a file using commands.
     */
    public void setInitialized(){
        isInitialized = true;
    }
}
