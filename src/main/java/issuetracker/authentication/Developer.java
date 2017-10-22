package issuetracker.authentication;

import issuetracker.cli.view.Command;

import java.util.LinkedHashMap;

import issuetracker.cli.view.LogoutCommand;
import issuetracker.clustering.Issue;

import java.util.HashMap;
import java.util.Map;

public class Developer extends User {

    public final String value = "Dev";
    private Map<String, Issue> issueMap;

    public Developer() {
        super();
        issueMap = new HashMap<>();
    }

    public Developer(String email, String password) {
        super(email, password);

        issueMap = new HashMap<>();
    }


    public void addIssue(Issue issue) {
        issueMap.put(issue.getId(), issue);
    }

    public void removeIssue(Issue issue) {
        issueMap.remove(issue.getId(), issue);
    }

    public Map<String, Issue> getIssueMap() {
        return issueMap;
    }

    public void setIssueMap(Map<String, Issue> issueMap) {
        this.issueMap = issueMap;
    }
}
