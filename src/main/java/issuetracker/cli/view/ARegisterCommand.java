package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;

import java.util.InvalidPropertiesFormatException;

/**
 * Handles logic for registering a user if logged in as an admin
 */
public class ARegisterCommand extends Command{

    public ARegisterCommand() {
        super();
    }

    @Override
    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {
        String[] parts = userInput.split(" ");

        if (parts.length == 1) {
            if (parts[0].toUpperCase().equals("BACK")) {
                cliManager.showMenu();
            } else {
                System.out.println("Did not recognise command");
                cliManager.registerCLI();
            }
        }

        try {
            //registers user via authentication manager if valid cridentials are given
            authenticationManager.addUser(parts[0], parts[1]);
            cliManager.registerCLI();
        } catch (InvalidPropertiesFormatException e) {
            System.out.println(e.getMessage() + ". Please try again");
            cliManager.registerCLI();
        }
    }
}
