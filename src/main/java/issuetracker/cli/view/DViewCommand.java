package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.authentication.User;
import issuetracker.cli.CLIManager;
import issuetracker.clustering.Issue;
import issuetracker.db.FirebaseAdapter;

import java.util.List;

public class DViewCommand extends Command{

    @Override
    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {
        FirebaseAdapter firebaseAdapter = new FirebaseAdapter();

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
        User currentUser = authenticationManager.getCurrentUser();

        if (issue.getAssignees().contains(currentUser.getEmail())) {
            System.out.print("Go [BACK] to view all issues: ");
        } else {
            System.out.print("[ASSIGN] to yourself, or go [BACK] to view all issues: ");

        }

        String userCommand = cliManager.retrieveUserInput();

        switch (userCommand.toUpperCase()) {
            case "ASSIGN":
                cliManager.getIssueManager().assignIssue(issue, (Developer) currentUser);
                cliManager.viewIssuesCLI();
                break;
            case "BACK":
                cliManager.viewIssuesCLI();
                break;
            default:
                System.out.println("Did not recognise command");
                assignPrompt(authenticationManager, cliManager, issue);
                break;
        }
    }


}
