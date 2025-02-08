package innkeeper.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import innkeeper.Storage;
import innkeeper.TaskList;
import innkeeper.Ui;

public class MarkCommandTest {
    @Test
    public void parameterParseTest(){
        // test if the parameters are parsed correctly
        MarkCommand markCommand = new MarkCommand();

        try{
            TaskList tasks = new TaskList();
            Storage storage = new Storage("data/tasks.txt", null);
            Ui ui = new Ui();
            tasks.addTask(new innkeeper.task.DeadlineTask("return book", "2021-09-30"));

            markCommand = (MarkCommand) markCommand.parse("mark 1");
            try{
                markCommand.execute(tasks, storage, ui);
                assertEquals(true, true);
            } catch (Exception e){
            }

            markCommand = (MarkCommand) markCommand.parse("mark 2");
            try{
                markCommand.execute(tasks, storage, ui);
                assertEquals(true, false);
            } catch (Exception e){
                assertEquals("There is no task at index 2.", e.getMessage());
            }

            //Index 1 out of bounds for length 1
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
