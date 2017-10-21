package issuetracker.authentication;

import issuetracker.cli.view.Command;
import issuetracker.cli.view.ICommand;
import issuetracker.clustering.Issue;

import java.util.HashMap;
import java.util.Map;

public class Developer extends User {

    private HashMap<String, ICommand> viewMap;

    private Map<String, Issue> issueMap;

    public Developer() {
        super();
        issueMap = new HashMap<>();
    }

    public Developer(String email, String password) {
        super(email, password);

        viewMap = new HashMap<String, ICommand>();
        viewMap.put("V", new Command());
        viewMap.put("M", new Command());
        viewMap.put("L", new Command());

        issueMap = new HashMap<>();
    }

    @Override
    public HashMap<String, ICommand> getView() {
        return this.viewMap;
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
