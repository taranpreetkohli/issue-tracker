package issuetracker.authentication;

import issuetracker.cli.view.Command;
import issuetracker.cli.view.ICommand;
import issuetracker.clustering.Issue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Developer extends User {

    private HashMap<String, ICommand> viewMap;

    private Set<Issue> issueSet;

    public Developer() {
        super();
        issueSet = new HashSet<>();
    }

    public Developer(String email, String password) {
        super(email, password);

        viewMap = new HashMap<String, ICommand>();
        viewMap.put("V", new Command());
        viewMap.put("M", new Command());
        viewMap.put("L", new Command());

        issueSet = new HashSet<>();
    }

    @Override
    public HashMap<String, ICommand> getView() {
        return this.viewMap;
    }

    public void addIssue(Issue issue) {
        issueSet.add(issue);
    }

    public void removeIssue(Issue issue) {
        issueSet.remove(issue);
    }

    public Set<Issue> getIssueSet() {
        return issueSet;
    }

    public void setIssueSet(Set<Issue> issueSet) {
        this.issueSet = issueSet;
    }
}
