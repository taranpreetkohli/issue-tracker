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

/**
 * Displays the command line interface and handles user input to the system
 */
public class CLIManager {
    private AuthenticationManager authenticationManager;
    private IssueManager issueManager;
    private Map<String, Command> viewMap;
    private String commandSet;

    public CLIManager(AuthenticationManager authenticationManager, IssueManager issueManager) {
        this.authenticationManager = authenticationManager;
        this.issueManager = issueManager;
    }

    /**
     * Prints menu to command line
     */
    public void showMenu() {
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

        promptMenu();
    }

    /**
     * Prompts user input based on menu options
     */
    private void promptMenu() {
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
            promptMenu();
        }
    }

    /**
     * Login command line display.
     * Takes user input and logs in via the authentication manager
     * Sets us relevant user command and view map information if login is successful
     */
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
                    //Initiate linkedhashmap of custom command objects for administrators
                    this.viewMap = new LinkedHashMap<>();
                    viewMap.put("R", new ARegisterCommand());
                    viewMap.put("V", new AViewCommand());
                    viewMap.put("M", new AManageCommand());
                    viewMap.put("L", new LogoutCommand());
                    commandSet = "R/V/M/L";
                } else if (authenticationManager.getCurrentUser() instanceof Developer) {
                    //Initiate linkedhashmap of custom command objects for developers
                    this.viewMap = new LinkedHashMap<>();
                    viewMap.put("V", new DViewCommand());
                    viewMap.put("M", new DManageCommand());
                    viewMap.put("L", new LogoutCommand());
                    commandSet = "V/M/L";
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

    /**
     * Register user command line display
     * Runs corresponding command logic if valid inputs are given
     */
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
                registerCLI();
            }
        }
    }

    /**
     * View issues command line display
     */
    public void viewIssuesCLI() {
        //Retrieves all issues from firebase in order of priority
        List<Issue> allIssues = issueManager.retrieveIssuesOrderedByPriority();

        if (allIssues != null) {
            printIssueList(allIssues);
        }
        handleViewIdInput();
    }

    /**
     * Handles user input to view issues when on view 'screen'
     * Invokes respective command logic for view when valid input is given
     */
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

    /**
     * Manage issues command line display
     * Admin and Developer Manage Issues views are different and handled respectively
     */
    public void manageIssuesCLI() {
        //Show issues assigned to dev (ID TITLE)
        User currentUser = authenticationManager.getCurrentUser();
        List<Issue> allIssues = issueManager.retrieveIssuesOrderedByPriority();

        //Displays issues currently assigned to the developer in or of priority
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
            //If current user is an admin, displays all issues and all questions/forum posts currently unassigned to an issue
            printIssueList(allIssues);
            List<Question> questionList = issueManager.retrieveUnassignedQuestions();
            printQuestionsList(questionList);
            handleAManageInput();
        }

    }

    /**
     * Invokes respective command logic when correct input is provided to manage view when logged in as an admin
     */
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

    /**
     * Invokes respective command logic when correct input is provided to manage view when logged in as an developer
     */
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

    /**
     * Logout command line display
     * Invokes logout logic from authenticationmanager if confirmation is given
     */
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

    /**
     * Prints list of unassigned questions
     * @param questionList
     */
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

    /**
     * Prints list of issues
     * @param issueList
     */
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

    /**
     * Retrieves users input to the command line
     * @return
     */
    public String retrieveUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Checks if given command is valid and able to be executed
     * @param command
     * @return
     */
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

    /**
     * Checks if number of words in command given by user matches the expected number
     * @param input
     * @param num
     * @return
     */
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

    /**
     * Checks user logout confirmation input format
     * @param input
     * @return
     */
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

    /**
     * Checks user confirmation
     * @param input
     * @return
     */
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
