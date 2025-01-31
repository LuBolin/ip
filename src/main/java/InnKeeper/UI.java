package innkeeper;

import java.util.Scanner;

public class Ui {
    private final String LINE_SEPARATOR = "____________________________________________________________";
    private boolean isInitialized = false;

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

    public void printFarewell() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Farewell, traveller!");
        System.out.println(LINE_SEPARATOR);
    }

    public void printLine(){
        System.out.println(LINE_SEPARATOR);
    }

    public void printMessage(String message) {
        if (!isInitialized){
            return;
        }
        System.out.println(LINE_SEPARATOR);
        System.out.println(message);
        System.out.println(LINE_SEPARATOR);
    }

    public String readCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void setInitialized(){
        isInitialized = true;
    }
}
