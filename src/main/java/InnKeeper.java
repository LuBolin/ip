import java.util.Scanner;

public class InnKeeper {
    static final String lineSeparator = "____________________________________________________________";

    public static void main(String[] args) {
        printGreetings();

        echoUserInput();

        printFarewell();
    }

    public static void printGreetings() {
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

        System.out.println(lineSeparator);
        System.out.println("Greetings! I'm the InnKeeper.");
        System.out.println("Fancy a beer?");
        System.out.println(lineSeparator);
        // Print an empty line for better readability of user input
        System.out.println();
    }

    public static void printFarewell() {
        System.out.println(lineSeparator);
        System.out.println("Farewell, traveller!");
        System.out.println(lineSeparator);
    }

    public static void echoUserInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        while (!userInput.equals("bye")) {
            System.out.println(lineSeparator);
            System.out.println(userInput);
            System.out.println(lineSeparator);
            userInput = sc.nextLine();
        }
    }

}
