package issuetracker.cli;

import issuetracker.exception.NoInputException;

import java.util.HashMap;

public class CLIManager {

    public void startCLI() {}

    public boolean isValidCommand(String command, HashMap view) {
        if (command.isEmpty()){
            throw new NoInputException("No command given");
        }

        if (view.keySet().contains(command)){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUserDetailFormat(String validInput) {

        if (validInput.isEmpty()){
            throw new NoInputException("No Email or Password Provided");
        }

        String[] parts = validInput.split(" ");
        if (parts.length == 2){
            return true;
        } else {
            return false;
        }
    }
}
