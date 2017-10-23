package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;

public class LogoutCommand extends Command{

    public LogoutCommand() {
        super();
    }

    @Override
    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {

        try {
            authenticationManager.logout();
            cliManager.loginCLI();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            cliManager.loginCLI();
        }
    }
}
