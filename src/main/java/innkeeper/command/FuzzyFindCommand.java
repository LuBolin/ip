package innkeeper.command;

import java.util.List;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.Task;


/**
 * Represents a command to find tasks that contain a keyword.
 */
public class FuzzyFindCommand extends Command {
    private final int DEFAULT_MATCH_PERCENTAGE = 70;
    private String keyword;
    private int matchPercentage = DEFAULT_MATCH_PERCENTAGE;

    @Override
    public CommandOutput execute(TaskList tasks, Storage storage, Ui ui) {
        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:\n");
        List<Task> userTasks = tasks.getTasks();
        boolean hasFound = false;
        // round to nearest character count, not up and not down
        int subLength = (int) Math.round(keyword.length() * matchPercentage / 100.0);

        for (int i = 0; i < userTasks.size(); i++) {
            String taskStr = userTasks.get(i).toString().toLowerCase();
            if (containsSubstringMatch(keyword, taskStr, subLength)) {
                hasFound = true;
                message.append((i + 1)).append(". ").append(userTasks.get(i)).append("\n");
            }

        }
        if (!hasFound) {
            message = new StringBuilder("There are no tasks in the list that match the keyword.");
        }
        ui.printMessage(message.toString());
        CommandOutput output = new CommandOutput(TerminationType.CONTINUE, message.toString());
        return output;
    }

    @Override
    public Command parse(String input) throws Exception {
        String[] tokens = input.split(" ", 2);
        String formatExceptionMessage = "Usage: ffind <keyword> -i <match percentage>."
            + "\nThe percentage is rounded down to the nearest character count."
            + "\nIf not provided (-i <percentage> is optional), the percentage defaults to 70."
            + "\nExample: ffind dog -i 50 would return all task with do or og in them."
            + "\nExplanation: (50%) * 3 = 1.5, rounding to 2.";

        if (tokens.length < 2) {
            throw new Exception(formatExceptionMessage);
        }

        String parameters = tokens[1];
        boolean hasPercentage = parameters.contains(" -i ");

        if (!hasPercentage) {
            keyword = parameters;
            return this;
        }

        String[] keywordAndPercentage = parameters.split(" -i ");
        if (keywordAndPercentage.length != 2) {
            throw new Exception(formatExceptionMessage);
        }
        keyword = keywordAndPercentage[0];
        try {
            matchPercentage = Integer.parseInt(keywordAndPercentage[1]);
        } catch (NumberFormatException e) {
            throw new Exception("Match percentage must be an integer.");
        }
        if (matchPercentage < 1 || matchPercentage > 100) {
            throw new Exception("Match percentage must be between 1 and 100.");
        }
        return this;
    }

    /**
     * Checks if a task contains a substring match of a keyword.
     *
     * @param keyword The keyword to search for.
     * @param taskStr The task string to search in.
     * @param subLength The length of the substring to search for.
     * @return True if the task contains a substring match of the keyword, false otherwise.
     */
    private boolean containsSubstringMatch(String keyword, String taskStr, int subLength) {
        for (int i = 0; i <= keyword.length() - subLength; i++) {
            String sub = keyword.substring(i, i + subLength);
            if (taskStr.contains(sub)) {
                return true;
            }
        }
        return false;
    }
}
