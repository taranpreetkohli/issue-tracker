package issuetracker.cli;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.clustering.ClusterManager;
import issuetracker.exception.NoInputException;

import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

public class CLIManager {
    private AuthenticationManager authenticationManager;
    private ClusterManager clusterManager;

    public CLIManager(AuthenticationManager authenticationManager, ClusterManager clusterManager) {
        this.authenticationManager = authenticationManager;
        this.clusterManager = clusterManager;
    }

    public void startCLI() {
        System.out.print("Login using your [email password]: ");
        String userInput = retrieveUserInput();

        if (checkUserDetailFormat(userInput)) {
            String[] parts = userInput.split(" ");
            try {
                authenticationManager.login(parts[0], parts[1]);
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public String retrieveUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public boolean isValidCommand(String command) {
        if (command.isEmpty()){
            throw new NoInputException("No command given");
        }

        if (authenticationManager.getCurrentUser().getView().keySet().contains(command)){
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
