package innkeeper;

import innkeeper.command.Command;
import innkeeper.command.CommandOutput;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import java.util.Timer;
import java.util.TimerTask;

public class MainWindow extends BorderPane {

    @FXML
    private BorderPane root;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Button exitButton;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image keeperImage = new Image(this.getClass().getResourceAsStream("/images/InnKeeper.png"));


    private InnKeeper innKeeper;
    boolean hasStopped = false;

    /**
     * Initializes the MainWindow.
     * It binds the scrollPane to the dialogContainer.
     * It also sets the action for the sendButton to handle the user input.
     */
    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Bind the send button to the handleUserInput method
        sendButton.setOnAction((event) -> handleUserInput());
        // Bind the enter key to the sendButton
        userInput.setOnAction((event) -> handleUserInput());
        // Bind the exit button to mimic the user input "bye"
        exitButton.setOnAction((event) -> onExitButtonClicked());
    }

    public void setInnKeeper(InnKeeper innKeeper) {
        this.innKeeper = innKeeper;
        // add welcome message
        boolean withAsciiArt = false;
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(innKeeper.getGreetings(withAsciiArt), keeperImage)
        );
    }

    /**
     * Handles the user input.
     * It gets the user input, sends it to the chatbot, and displays the response.
     */
    @FXML
    private void handleUserInput() {
        if (hasStopped) {
            return;
        }
        String input = userInput.getText();
        CommandOutput output = innKeeper.getResponse(input);
        String response = output.getResponse();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, keeperImage)
        );
        userInput.clear();
        if (output.getTermination() == Command.TerminationType.TERMINATE) {
            exit();
        }
    }

    private void exit() {
        hasStopped = true;
        userInput.setDisable(true);
        Timer timer = new Timer();
        TimerTask terminateTask = new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        int delay = 1000; // 1 second
        timer.schedule(terminateTask, delay);
    }

    private void onExitButtonClicked() {
        String input = "bye";
        userInput.setText(input);
        handleUserInput();
    }
}
