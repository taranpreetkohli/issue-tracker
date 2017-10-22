package issuetracker.cli.view;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.cli.CLIManager;
import issuetracker.clustering.Issue;
import issuetracker.db.FirebaseAdapter;
import issuetracker.exception.UserException;

import java.util.List;

public class AViewCommand extends Command{
    private FirebaseAdapter firebaseAdapter = new FirebaseAdapter();

    @Override
    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {
        Issue issue = firebaseAdapter.getIssue(userInput);
        System.out.println(issue.getStatus() + " " + issue.getId() + ": " + issue.getTitle());
        System.out.println(issue.getSummary());

        List<String> assignedUsers = issue.getAssignees();

        if (!assignedUsers.isEmpty()) {
            System.out.println("Current Assignees: ");
            for (String user : assignedUsers) {
                System.out.println(user);
            }
        }

        assignPrompt(authenticationManager, cliManager, issue);
    }

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
                        cliManager.getIssueManager().assignIssue(admin, issue, (Developer)firebaseAdapter.getUser(parts[1]));
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
