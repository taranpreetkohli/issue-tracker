package issuetracker.cli;

import issuetracker.exception.NoInputException;

public class CLIManager {

    public boolean isValidCommand(String command) { return false; }

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
