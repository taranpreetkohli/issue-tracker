package issuetracker.authentication;

import issuetracker.clustering.Issue;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Developer model
 */
public class Developer extends User {

    /**
     * Stores the list of issue IDs associated with the issues this Developer is assigned to
     */
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
