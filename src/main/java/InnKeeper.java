public class InnKeeper {
    static final String lineSeparator = "____________________________________________________________";

    public static void main(String[] args) {
        printGreetings();

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
        System.out.println("Hello! I'm InnKeeper");
        System.out.println("What can I do for you?");
        System.out.println(lineSeparator);
    }

    // function to end the program
    public static void printFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(lineSeparator);
    }
}
