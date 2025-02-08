package innkeeper.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;
import innkeeper.task.DeadlineTask;

public class DeadlineCommandTest {
    @Test
    public void parameterParseTest(){
        // test if the parameters are parsed correctly
        DeadlineCommand deadlineCommand = new DeadlineCommand();
        try{
            deadlineCommand = (DeadlineCommand) deadlineCommand.parse("deadline return book /by 2021-09-30");
            String expectedToString = "[D][ ] return book (by: Sep 30 2021)";
            TaskList tasks = new TaskList();
            Storage storage = new Storage("data/tasks.txt", null);
            Ui ui = new Ui();
            deadlineCommand.execute(tasks, storage, ui);
            DeadlineTask deadlineTask = (DeadlineTask) tasks.getTask(0);
            assertEquals("[D][ ] return book (by: Sep 30 2021)", deadlineTask.toString());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
