package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;

public class Command {

    public final String value = "Command";
    protected String userInput;

    public Command() { }

    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

}
