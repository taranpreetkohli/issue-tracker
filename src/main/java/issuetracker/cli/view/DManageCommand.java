package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.authentication.User;
import issuetracker.cli.CLIManager;
import issuetracker.clustering.Issue;
import issuetracker.db.FirebaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DManageCommand extends Command{
    private final List<String> validDevCommand = new ArrayList<>(Arrays.asList("unassign", "close"));

    @Override
    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {
        String[] parts = userInput.split(" ");
        User currentUser = authenticationManager.getCurrentUser();
        FirebaseAdapter firebaseAdapter = new FirebaseAdapter();

        if (currentUser instanceof Developer) {
            if (validDevCommand.contains(parts[0].toLowerCase())) {
                //check if id valid
                if (((Developer) currentUser).getIssues().contains(parts[1])) {
                    switch (parts[0].toUpperCase()) {
                        case "UNASSIGN":
                            Issue issue = firebaseAdapter.getIssue(parts[1]);

                            if (issue.getAssignees().size() == 1) {
                                issue.setStatus(Issue.IssueStatus.UNASSIGNED);
                            }

                            cliManager.getIssueManager().unAssignIssue(issue, (Developer)currentUser);

                            break;
                        case "CLOSE":
                            cliManager.getIssueManager().resolveIssue((Developer)currentUser, firebaseAdapter.getIssue(parts[1]));
                            break;
                    }

                    cliManager.manageIssuesCLI();
                } else {
                    System.out.println("You cannot manage issue: \"" + parts[1] + "\"");
                    cliManager.handleManageInput();
                }
            } else {
                System.out.println("\"" + parts[0] + "\" is not recognised");
                cliManager.handleManageInput();
            }
        }
    }
}
