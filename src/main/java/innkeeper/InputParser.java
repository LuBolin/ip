package innkeeper;

import innkeeper.command.ByeCommand;
import innkeeper.command.Command;
import innkeeper.command.DeadlineCommand;
import innkeeper.command.DeleteCommand;
import innkeeper.command.EventCommand;
import innkeeper.command.FindCommand;
import innkeeper.command.FuzzyFindCommand;
import innkeeper.command.ListCommand;
import innkeeper.command.MarkCommand;
import innkeeper.command.TodoCommand;
import innkeeper.command.UnmarkCommand;

/**
 * Class in charge of parsing user input into commands.
 */
public class InputParser {

    /**
     * Parses the user input into a command.
     *
     * @param userInput The user input.
     * @return The command.
     * @throws Exception If the user input is invalid.
     */
    public Command parseUserInput(String userInput) throws Exception {

        assert userInput != null : "User input string should not be null";

        // Choose to not use '//d+'
        // As user may just type 'mark' or 'unmark' or 'delete' without any index
        // And we want to still instruct them to input an index
        // Instead of parsing that input command as nonsensical
        String markAndUnmarkRegex = "(mark|unmark).*";
        String deleteRegex = "delete.*";
        String newTaskRegex = "(todo|deadline|event).*";
        Command output = null;

        if (userInput.equals("bye")) {
            output = new ByeCommand().parse(userInput);
        } else if (userInput.equals("list")) {
            output = new ListCommand().parse(userInput);
        } else if (userInput.matches(markAndUnmarkRegex)) {
            boolean isMark = userInput.startsWith("mark");
            if (isMark) {
                output = new MarkCommand().parse(userInput);
            } else {
                output = new UnmarkCommand().parse(userInput);
            }
        } else if (userInput.matches(deleteRegex)) {
            output = new DeleteCommand().parse(userInput);
        } else if (userInput.matches(newTaskRegex)) { // Add a new task
            output = parseNewTaskInput(userInput);
        } else if (userInput.startsWith("fuzzyfind") || userInput.startsWith("ffind")) {
            output = new FuzzyFindCommand().parse(userInput);
        } else if (userInput.startsWith("find")) {
            output = new FindCommand().parse(userInput);
        }
        else {
            String exceptionMessage = "I'm sorry, but I don't know what that means.\n"
                    + "Task types: todo, deadline, event.\n"
                    + "Other commands: list, find, fuzzyfind (ffind), delete, mark, unmark.\n"
                    + "If you are leaving, just say \"bye\".";
            throw new IllegalArgumentException(exceptionMessage);
        }
        return output;
    }

    /**
     * Specifically parses the user input into a new task command.
     *
     * @param userInput The user input.
     * @return The command.
     * @throws Exception If the user input is invalid.
     */
    private Command parseNewTaskInput(String userInput) throws Exception {
        Command output = null;

        if (userInput.startsWith("todo")) {
            output = new TodoCommand().parse(userInput);
        } else if (userInput.startsWith("deadline")) {
            output = new DeadlineCommand().parse(userInput);
        } else if (userInput.startsWith("event")) {
            output = new EventCommand().parse(userInput);
        }

        return output;
    }
}
