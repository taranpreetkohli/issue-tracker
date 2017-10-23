package issuetracker.cli;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.authentication.User;
import issuetracker.cli.view.*;
import issuetracker.clustering.Issue;
import issuetracker.clustering.Question;
import issuetracker.exception.NoInputException;
import issuetracker.clustering.IssueManager;

import java.util.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

public class CLIManager {
    private AuthenticationManager authenticationManager;
    private IssueManager issueManager;
    private Map<String, Command> viewMap;

    public CLIManager(AuthenticationManager authenticationManager, IssueManager issueManager) {
        this.authenticationManager = authenticationManager;
        this.issueManager = issueManager;
    }

    public void loginCLI() {
        System.out.print("Login using your [email password]: ");
        String userInput = retrieveUserInput();
        boolean isCorrectFormat = false;

        try {
            isCorrectFormat = checkNumInputFormat(userInput, 2);
        } catch (NoInputException e) {
            loginCLI();
        }

        if (isCorrectFormat) {
            String[] parts = userInput.split(" ");
            try {
                authenticationManager.login(parts[0], parts[1]);

                if (authenticationManager.getCurrentUser() instanceof Administrator) {
                    this.viewMap = new LinkedHashMap<>();
                    viewMap.put("R", new ARegisterCommand());
                    viewMap.put("V", new AViewCommand());
                    viewMap.put("M", new AManageCommand());
                    viewMap.put("L", new LogoutCommand());
                } else if (authenticationManager.getCurrentUser() instanceof Developer) {
                    this.viewMap = new LinkedHashMap<>();
                    viewMap.put("V", new DViewCommand());
                    viewMap.put("M", new DManageCommand());
                    viewMap.put("L", new LogoutCommand());
                }

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
        if (authenticationManager.getCurrentUser() instanceof Administrator) {
            System.out.println("Register a developer using a valid email and password. Password must be 8 or more characters containing no spaces");
            System.out.print("Please enter the details in the format [email password] or [BACK] to return to menu: ");

            String userInput = retrieveUserInput();

            try {
                if (checkNumInputFormat(userInput, 1) || checkNumInputFormat(userInput, 2)) {
                    this.viewMap.get("R").setUserInput(userInput);
                    this.viewMap.get("R").run(authenticationManager, this);
                } else {
                    System.out.println("Email and password not entered in correct format");
                    registerCLI();
                }
            } catch (NoInputException e) {
                handleAManageInput();
            }
        }
    }

    public void viewIssuesCLI() {
        System.out.println("Invoking view issues logic");
        List<Issue> allIssues = issueManager.retrieveIssuesOrderedByPriority();

        if (allIssues != null) {
            printIssueList(allIssues);
        }
        handleViewIdInput();
    }

    public void handleViewIdInput() {
        System.out.print("Enter [id] to view more details or [BACK] to go back to main menu: ");
        String userInput = retrieveUserInput();

        boolean isCorrectFormat = false;

        try {
            isCorrectFormat = checkNumInputFormat(userInput, 1);
        } catch (NoInputException e) {
            handleViewIdInput();
        }

        if (isCorrectFormat) {
            this.viewMap.get("V").setUserInput(userInput);
            this.viewMap.get("V").run(authenticationManager, this);
        } else {
            System.out.println("Did not recognise command");

            handleViewIdInput();
        }
    }

    public void manageIssuesCLI() {
        //Show issues assigned to dev (ID TITLE)
        User currentUser = authenticationManager.getCurrentUser();
        List<Issue> allIssues = issueManager.retrieveIssuesOrderedByPriority();


        if (currentUser instanceof Developer) {
            List<String> devIssues = ((Developer) currentUser).getIssues();

            if (!devIssues.isEmpty()) {
                for (Issue issue : allIssues) {
                    if (devIssues.contains(issue.getId())) {
                        if (issue.getStatus() != Issue.IssueStatus.RESOLVED) {
                            String id = issue.getId();
                            String title = issue.getTitle();
                            System.out.println(id + ": " + title);
                        }
                    }
                }
            } else {
                System.out.println("You currently have no issues to manage");
            }

            handleDManageInput();
        } else {
            printIssueList(allIssues);
            List<Question> questionList = issueManager.retrieveUnassignedQuestions();
            printQuestionsList(questionList);
            handleAManageInput();
        }

    }

    public void handleAManageInput() {
        System.out.print("[VIEW I/Q id] to see more details, [ASSIGN ISSUEID QUESTIONID] to assign question to issue or [BACK] to go back to main menu: ");

        String userInput = retrieveUserInput();

        try {
            if (checkNumInputFormat(userInput, 1) || checkNumInputFormat(userInput, 3)) {
                this.viewMap.get("M").setUserInput(userInput);
                this.viewMap.get("M").run(authenticationManager, this);
            } else {
                System.out.println("Did not recognise command");
                handleAManageInput();
            }
        } catch (NoInputException e) {
            handleAManageInput();
        }
    }

    private void printQuestionsList(List<Question> questionList) {
        if (questionList != null) {
            System.out.println("UNASSIGNED QUESTIONS");
            for (Question question : questionList) {
                long id = question.getQuestionID();
                String qTitle = question.getQuestion();
                System.out.println(id + ": " + qTitle);
            }
        } else {
            System.out.println("There are no unassigned issues to display!");
        }
    }

    //Helper for printing issues
    private void printIssueList(List<Issue> issueList) {
        if (issueList != null) {
            System.out.println("ISSUES");
            for (Issue issue : issueList) {
                String id = issue.getId();
                String status = issue.getStatus().toString();
                String title = issue.getTitle();
                System.out.println(status + " " + id + ": " + title);
            }
        } else {
            System.out.println("There are no issues to display!");
        }
    }

    public void handleDManageInput() {
        System.out.println("Enter [close/unassign id] to manage an issue or [BACK] to go back to main menu: ");
        String userInput = retrieveUserInput();

        try {
            if (checkNumInputFormat(userInput, 2) || checkNumInputFormat(userInput, 1)) {
                this.viewMap.get("M").setUserInput(userInput);
                this.viewMap.get("M").run(authenticationManager, this);
            } else {
                System.out.println("Did not recognise command");
                handleDManageInput();
            }
        } catch (NoInputException e) {
            handleDManageInput();
        }
    }

    public void showMenu() {
        User currentUser = authenticationManager.getCurrentUser();
        String commandSet;

        if (currentUser instanceof Administrator) {
            commandSet = "R/V/M/L";
        } else {
            commandSet = "V/M/L";
        }

        for (Map.Entry<String, Command> entry : this.viewMap.entrySet()) {
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

        if (this.viewMap.keySet().contains(command.toUpperCase())){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkNumInputFormat(String input, int num) {
        if (input.isEmpty()){
            throw new NoInputException("Nothing was entered");
        }

        String[] parts = input.split(" ");
        if (parts.length == num){
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
                this.viewMap.get("L").run(authenticationManager, this);
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

    public void setViewMap(Map<String, Command> viewMap) {
        this.viewMap = viewMap;
    }

    public IssueManager getIssueManager() {
        return issueManager;
    }
}
