package issuetracker.authentication;

import issuetracker.clustering.Issue;

import java.util.ArrayList;
import java.util.List;

public class Developer extends User {

    public final String value = "Dev";
    private List<String> issues;

    public Developer() {
        super();
        issues = new ArrayList<>();
    }

    public Developer(String email, String password) {
        super(email, password);
        issues = new ArrayList<>();
    }

    public void addIssue(Issue issue) {
        issues.add(issue.getId());
    }

    public void removeIssue(Issue issue) {
        issues.remove(issue.getId());
    }

    public List<String> getIssues() {
        return issues;
    }

    public void setIssues(List<String> issues) {
        this.issues = issues;
    }
}
