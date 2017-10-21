package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;

import java.util.InvalidPropertiesFormatException;

public class ARegisterCommand extends Command{

    public ARegisterCommand() {
        super();
    }

    @Override
    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {
        String[] parts = userInput.split(" ");
        try {
            authenticationManager.addUser(parts[0], parts[1]);
        } catch (InvalidPropertiesFormatException e) {
            System.out.println(e.getMessage() + ". Please try again");
            cliManager.registerCLI();
        }
    }
}
