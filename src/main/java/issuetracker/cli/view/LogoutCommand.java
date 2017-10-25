package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;

/**
 * Handles logic for logging out for all users
        */
public class LogoutCommand extends Command{

    public LogoutCommand() {
        super();
    }

    @Override
    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {

        try {
            //calls authentication logout logic to update database status of user
            authenticationManager.logout();
            cliManager.loginCLI();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            cliManager.loginCLI();
        }
    }
}
