package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;

/**
 * Abstract command class. Children need to implement the run method
 */
public abstract class Command {
    protected String userInput;

    public Command() { }

    public abstract void run(AuthenticationManager authenticationManager, CLIManager cliManager);

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

}
