package issuetracker.cli.view;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;
import issuetracker.clustering.Issue;
import issuetracker.clustering.Question;
import issuetracker.db.DBContext;

import java.util.List;

public class AManageCommand extends Command {
    private DBContext dBContext = new DBContext();
    private CLIManager cliManager;

    @Override
    public void run(AuthenticationManager authenticationManager, CLIManager cliManager) {
        this.cliManager = cliManager;
        if (userInput.toUpperCase().equals("BACK")) {
            cliManager.showMenu();
        }

        String[] parts = userInput.split(" ");

        switch (parts[0].toUpperCase()) {
            case "VIEW":
                viewDetails(parts);
                cliManager.manageIssuesCLI();
                break;
            case "ASSIGN":
                assignQuestionToIssue(parts);
                cliManager.manageIssuesCLI();
                break;
            default:
                System.out.println("Did not recognise command");
                cliManager.handleAManageInput();
                break;
        }



    }

    private void viewDetails(String[] parts) {
        switch (parts[1].toUpperCase()) {
            case "I":
                //display questions in issue
                displayIssue(parts[2]);

                break;
            case "Q":
                //display question into
                displayQuestion(parts[2]);
                break;
            default:
                System.out.println("Did not recognise command");
                cliManager.handleAManageInput();
                break;
        }
    }

    private void displayIssue(String issueID) {
        Issue issue = dBContext.getIssue(issueID);

        if (issue == null) {
            System.out.println("Issue with ID: " + issueID + " does not exist");
            cliManager.handleAManageInput();
        }

        System.out.println("Issue ID: " + issueID + " Title: " + issue.getTitle());

        List<Question> issueQuestions = issue.getQuestions();

        if (issueQuestions.isEmpty()) {
            System.out.println("No questions assigned to this issue");
            cliManager.handleAManageInput();
        }

        for (Question question : issueQuestions) {
            long id = question.getQuestionID();
            String qTitle = question.getQuestion();

            System.out.println(id + ": " + qTitle);
        }

        unassignQuestionPrompt(issue);
    }

    private void displayQuestion(String questionID) {
        Question question = dBContext.getQuestion(questionID);

        if (question == null) {
            System.out.println("Question with ID: " + questionID + " does not exist");
            cliManager.handleAManageInput();
        }

        System.out.println("ID: " + questionID + " TITLE: " + question.getQuestion());
        System.out.println(question.getInformation());

        backToIQViewPrompt();

    }

    private void assignQuestionToIssue(String[] parts) {
        Issue issue = dBContext.getIssue(parts[1]);
        Question question = dBContext.getQuestion(parts[2]);

        if (issue == null) {
            System.out.println("Issue with ID: " + parts[1] + " does not exist");
            cliManager.handleAManageInput();
        }

        if (question == null) {
            System.out.println("Question with ID: " + parts[2] + " does not exist");
            cliManager.handleAManageInput();
        }

        cliManager.getIssueManager().addQuestion(issue, question);
        cliManager.manageIssuesCLI();
    }

    private void backToIQViewPrompt() {
        System.out.print("[BACK] to return to issues/questions list: ");
        String userCommand = cliManager.retrieveUserInput();

        if (userCommand.toUpperCase().equals("BACK")) {
            cliManager.manageIssuesCLI();
        } else {
            System.out.println("Did not recognise command");
            backToIQViewPrompt();
        }
    }

    private void unassignQuestionPrompt(Issue issue) {
        System.out.print("[UNASSIGN QUESTIONID] to unassign a question from an issue or [BACK] to return to issues/questions list: ");
        String userCommand = cliManager.retrieveUserInput();

        String[] userCommandParts = userCommand.split(" ");

        if ((userCommandParts.length != 2) && (userCommandParts.length != 1)) {
            System.out.println("Did not recognise command");
            unassignQuestionPrompt(issue);
        }

        if (userCommandParts.length == 1) {
            if (userCommandParts[0].toUpperCase().equals("BACK")) {
                cliManager.manageIssuesCLI();
            } else {
                System.out.println("Did not recognise command");
                unassignQuestionPrompt(issue);
            }
        }

        if (userCommandParts.length == 2) {

            if (userCommandParts[0].toUpperCase().equals("UNASSIGN")) {
                Question question = dBContext.getQuestion(userCommandParts[1]);

                if (question == null) {
                    System.out.println("Question with ID: " + userCommandParts[1] + " does not exist");
                    cliManager.handleAManageInput();
                }

                cliManager.getIssueManager().removeQuestion(issue, question);
                cliManager.manageIssuesCLI();
            } else {
                System.out.println("Did not recognise command");
                unassignQuestionPrompt(issue);
            }
        }

    }
}
