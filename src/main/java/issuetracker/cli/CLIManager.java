package issuetracker.cli;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.User;
import issuetracker.cli.view.ICommand;
import issuetracker.clustering.ClusterManager;
import issuetracker.exception.NoInputException;

import java.util.*;

public class CLIManager {
    private AuthenticationManager authenticationManager;
    private ClusterManager clusterManager;

    public CLIManager(AuthenticationManager authenticationManager, ClusterManager clusterManager) {
        this.authenticationManager = authenticationManager;
        this.clusterManager = clusterManager;
    }

    public void loginCLI() {
        System.out.print("Login using your [email password]: ");
        String userInput = retrieveUserInput();
        boolean isCorrectFormat = false;

        try {
            isCorrectFormat = checkUserDetailFormat(userInput);
        } catch (NoInputException e) {
            loginCLI();
        }

        if (isCorrectFormat) {
            String[] parts = userInput.split(" ");
            try {
                authenticationManager.login(parts[0], parts[1]);
            } catch (InvalidPropertiesFormatException e) {
                System.out.println(e.getMessage() + ". Please try again");
                loginCLI();
            } catch (InstantiationException e) {
                System.out.println(e.getMessage() + ". Please try again");
                loginCLI();
            }
        } else {
            System.out.println("Email and password not entered in correct format");
            loginCLI();
        }
    }

    public void registerCLI() {
        System.out.println("Register a developer using a valid email and password");
        System.out.println("Password must be 8 or more characters containing no spaces");
        System.out.println("Please enter the details in the format [email password]: ");

        String userInput = retrieveUserInput();
        System.out.println("Retrieved user input for register is: " + userInput);
        boolean isCorrectFormat = false;

        try {
            isCorrectFormat = checkUserDetailFormat(userInput);
        } catch (NoInputException e) {
            registerCLI();
        }

        if (isCorrectFormat) {
            authenticationManager.getCurrentUser().getView().get("R").run(authenticationManager, userInput, this);
        } else {
            System.out.println("Email and password not entered in correct format");
            registerCLI();
        }
    }

    public void showMenu() {
        User currentUser = authenticationManager.getCurrentUser();
        HashMap<String, ICommand> userView = currentUser.getView();
        String commandSet;

        if (currentUser instanceof Administrator) {
            commandSet = "R/V/M/L";
        } else {
            commandSet = "V/M/L";
        }

        for (Map.Entry<String, ICommand> entry : userView.entrySet()) {
            switch (entry.getKey()) {
                case "R":
                    System.out.println("(R)egister");
                    break;
                case "V":
                    System.out.println("(V)iew issues");
                    break;
                case "M":
                    System.out.println("(M)anage issues");
                    break;
                case "L":
                    System.out.println("(L)og out");
                    break;
                default:
                    break;
            }
        }

        System.out.println("Please enter your command (" + commandSet + "): ");

        String userInput = retrieveUserInput().toUpperCase();

        System.out.println("Passed retrieve user input, user input is: " + userInput);
        if (isValidCommand(userInput)) {
            System.out.println("User input is: " + userInput);
            switch (userInput) {
                    case "R":
                        System.out.println("In R case");
                        registerCLI();
                        break;
                    case "V":
                        break;
                    case "M":
                        break;
                    case "L":
                    break;

            }
        } else {
            System.out.println("Valid command false, user command is: " + userInput);
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

        if (authenticationManager.getCurrentUser().getView().keySet().contains(command.toUpperCase())){
            System.out.println("I'm true!");
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
