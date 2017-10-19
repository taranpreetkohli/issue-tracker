package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;

import java.util.InvalidPropertiesFormatException;

public class Command {

    private String value = "Register";

    public void run(AuthenticationManager authenticationManager, String userInput, CLIManager cliManager) {
        String[] parts = userInput.split(" ");
        try {
            authenticationManager.addUser(parts[0], parts[1]);
        } catch (InvalidPropertiesFormatException e) {
            System.out.println(e.getMessage() + ". Please try again");
            cliManager.registerCLI();
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
