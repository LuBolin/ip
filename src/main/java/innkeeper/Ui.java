package innkeeper;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class in charge of handling user interface.
 * It prints messages to the user and reads user input.
 */
public class Ui {
    private final String LINE_SEPARATOR = "____________________________________________________________";
    // Generated with https://patorjk.com/software/taag/
    // Font: "Big" (The font is called "Big" on the website)
    // Name: InnKeeper
    private final String ASCII_LOGO ="""
                 _____             _  __
                |_   _|           | |/ /                        \s
                  | |  _ __  _ __ | ' / ___  ___ _ __   ___ _ __\s
                  | | | '_ \\| '_ \\|  < / _ \\/ _ \\ '_ \\ / _ \\ '__|
                 _| |_| | | | | | | . \\  __/  __/ |_) |  __/ |  \s
                |_____|_| |_|_| |_|_|\\_\\___|\\___| .__/ \\___|_|  \s
                                                | |             \s
                                                |_|             \s
                """;

    private boolean isInitialized = false;

    /**
     * Returns the greetings message.
     */
    public String getGreetings(boolean withAsciiArt) {
        // Stopped using ascii art
        // as it only looks good in console and looks bad in JavaFX
        String message = "";
        if (withAsciiArt){
            message += ASCII_LOGO + "\n";
        }
        message += "Greetings! I'm the InnKeeper.\n" +
                "I can keep track of things for you.\n";
        return message;
    }

    /**
     * Prints the greetings message and returns it as a string.
     */
    public void printGreetings() {
        boolean withAsciiArt = true;
        String message = LINE_SEPARATOR;
        message += getGreetings(withAsciiArt);
        message += LINE_SEPARATOR;
        System.out.println(message);
    }

    /**
     * Prints the farewell message and returns it as a string.
     */
    public void printFarewell() {
        String message = LINE_SEPARATOR + "\n" + "Farewell, traveller!" + "\n" + LINE_SEPARATOR;
        System.out.println(message);
    }

    /**
     * Prints a line separator and returns it as a string.
     */
    public void printLine(){
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints a message to the user and returns it as a string.
     *
     * @param message The message to print.
     */
    public void printMessage(String message) {
        if (!isInitialized){
            return;
        }
        String formattedMessage = LINE_SEPARATOR + "\n" + message + "\n" + LINE_SEPARATOR;
        System.out.println(formattedMessage);
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
