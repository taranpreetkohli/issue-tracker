package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;

public interface ICommand {

    void run(AuthenticationManager authenticationManager, String userInput, CLIManager cliManager);
}
