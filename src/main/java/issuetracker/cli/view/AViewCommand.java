package issuetracker.cli.view;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.cli.CLIManager;
import issuetracker.clustering.Issue;
import issuetracker.db.DBContext;
import issuetracker.exception.UserException;

import java.util.List;

/**
 * Handles logic for viewing and assigning issues when logged in as an admin
 */
public class AViewCommand extends Command{
    private DBContext dBContext = new DBContext();

    @Override
    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {
        if (userInput.toUpperCase().equals("BACK")) {
            cliManager.showMenu();
        }

        Issue issue = dBContext.getIssue(userInput);

        if (issue == null) {
            System.out.println("Issue with ID: " + userInput + " does not exist");
            cliManager.handleViewIdInput();
        }

        System.out.println(issue.getStatus() + " " + issue.getId() + ": " + issue.getTitle());
        System.out.println(issue.getSummary());

        List<String> assignedUsers = issue.getAssignees();

        //Displays issue details as well as who is currently assigned
        if (!assignedUsers.isEmpty()) {
            System.out.println("Current Assignees: ");
            for (String user : assignedUsers) {
                System.out.println(user);
            }
        }

        assignPrompt(authenticationManager, cliManager, issue);
    }

    /**
     * Assigns an issue to a developer based on id and email provided by user
     * @param authenticationManager
     * @param cliManager
     * @param issue
     */
    private void assignPrompt(AuthenticationManager authenticationManager, CLIManager cliManager, Issue issue) {
        Administrator admin = (Administrator)authenticationManager.getCurrentUser();

        System.out.print("[ASSIGN email] to assign this issue to a developer, or go [BACK] to view all issues: ");

        String userCommand = cliManager.retrieveUserInput();
        if (userCommand.isEmpty()) {
            assignPrompt(authenticationManager,cliManager,issue);
        }

        String[] parts = userCommand.split(" ");

        switch (parts[0].toUpperCase()) {
            case "ASSIGN":
                //check length is 2
                if (parts.length == 2) {
                    try {
                        cliManager.getIssueManager().assignIssue(admin, issue, (Developer) dBContext.getUser(parts[1]));
                        cliManager.viewIssuesCLI();
                    } catch (UserException e) {
                        System.out.print(e.getMessage() + "! Please enter an existing developer email");
                        assignPrompt(authenticationManager, cliManager, issue);
                    }
                } else {
                    System.out.println("Incorrect command format");
                    assignPrompt(authenticationManager, cliManager, issue);
                }
                break;
            case "BACK":
                //check length is 1
                if (parts.length == 1) {
                    cliManager.viewIssuesCLI();
                } else {
                    System.out.println("Incorrect command format");
                    assignPrompt(authenticationManager, cliManager, issue);
                }
                break;
            default:
                System.out.println("Did not recognise command");
                assignPrompt(authenticationManager, cliManager, issue);
                break;
        }

    }
}
