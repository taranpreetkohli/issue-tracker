package issuetracker.cli;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.User;
import issuetracker.cli.view.Command;
import issuetracker.exception.NoInputException;
import issuetracker.clustering.IssueManager;

import java.util.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

public class CLIManager {
    private AuthenticationManager authenticationManager;
    private IssueManager issueManager;

    public CLIManager(AuthenticationManager authenticationManager, IssueManager issueManager) {
        this.authenticationManager = authenticationManager;
        this.issueManager = issueManager;
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
                showMenu();
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
        System.out.println("Register a developer using a valid email and password. Password must be 8 or more characters containing no spaces");
        System.out.print("Please enter the details in the format [email password]: ");

        String userInput = retrieveUserInput();
        boolean isCorrectFormat = false;

        try {
            isCorrectFormat = checkUserDetailFormat(userInput);
        } catch (NoInputException e) {
            registerCLI();
        }

        if (isCorrectFormat) {
            authenticationManager.getCurrentUser().getViewMap().get("R").setUserInput(userInput);
            authenticationManager.getCurrentUser().getViewMap().get("R").run(authenticationManager, this);
        } else {
            System.out.println("Email and password not entered in correct format");
            registerCLI();
        }
    }

    public void viewIssuesCLI() {
        System.out.println("Invoking view issues logic");
        authenticationManager.getCurrentUser().getViewMap().get("V").run(authenticationManager, this);
    }

    public void manageIssuesCLI() {
        System.out.println("Invoking view issues logic");
        authenticationManager.getCurrentUser().getViewMap().get("M").run(authenticationManager, this);
    }

    public void showMenu() {
        User currentUser = authenticationManager.getCurrentUser();
        Map<String, Command> userView = currentUser.getViewMap();
        String commandSet;

        if (currentUser instanceof Administrator) {
            commandSet = "R/V/M/L";
        } else {
            commandSet = "V/M/L";
        }

        for (Map.Entry<String, Command> entry : userView.entrySet()) {
            switch (entry.getKey()) {
                case "R":
                    System.out.println("(R)egister a developer");
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

        promptMenu(commandSet);
    }

    private void promptMenu(String commandSet) {
        System.out.print("Please enter your command (" + commandSet + "): ");

        String userInput = retrieveUserInput().toUpperCase();

        if (isValidCommand(userInput)) {
            switch (userInput) {
                case "R":
                    registerCLI();
                    break;
                case "V":
                    viewIssuesCLI();
                    break;
                case "M":
                    manageIssuesCLI();
                    break;
                case "L":
                    logoutCLI();
                    break;
            }
        } else {
            System.out.println("Sorry, that is not a valid command");
            promptMenu(commandSet);
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

        if (authenticationManager.getCurrentUser().getViewMap().keySet().contains(command.toUpperCase())){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUserDetailFormat(String input) {

        if (input.isEmpty()){
            throw new NoInputException("No Email or Password Provided");
        }

        String[] parts = input.split(" ");
        if (parts.length == 2){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUserConfirmationFormat(String input) {
        if (input.isEmpty()){
            throw new NoInputException("No Confirmation Provided");
        }

        String[] parts = input.split(" ");
        if (parts.length == 1 && (input.equals("Y") || input.equals("y") || input.equals("N")) || input.equals("n")){
            return true;
        } else {
            return false;
        }
    }

    public void logoutCLI() {
        System.out.println("Are you sure you want to logout?");
        System.out.println("Please enter [Y/y] to confirm, or [N/n] to cancel");

        String userInput = retrieveUserInput();
        boolean isCorrectFormat = false;

        try {
            isCorrectFormat = checkUserConfirmationFormat(userInput);
        } catch (NoInputException e) {
            logoutCLI();
        }

        if (isCorrectFormat) {
            if(checkUserConfirmation(userInput)){
                authenticationManager.getCurrentUser().getViewMap().get("L").run(authenticationManager, this);
            } else {
                showMenu();
            }
        } else {
            System.out.println("You must confirm by entering [Y/y], or cancel by entering [N/n])");
            showMenu();
        }
    }

    public boolean checkUserConfirmation(String input) {
        if (input.equals("Y") || input.equals("y")){
            return true;
        } else {
            return false;
        }
    }
}
