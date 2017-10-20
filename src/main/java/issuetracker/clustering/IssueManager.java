package issuetracker.clustering;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.Developer;
import issuetracker.authentication.User;
import issuetracker.db.FirebaseAdapter;
import issuetracker.exception.IssueNotFoundException;
import issuetracker.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssueManager {

    private static Logger logger = LoggerFactory.getLogger(IssueManager.class);
    private FirebaseAdapter firebaseAdapter;

    public IssueManager(FirebaseAdapter firebaseAdapter) {
        this.firebaseAdapter = firebaseAdapter;
    }

    public Issue generateCluster(String input) {
        return null;
    }

    public void addQuestion(Issue issue, Question question) {
        issue.addQuestion(question);
        firebaseAdapter.updateIssue(issue);
    }

    public void removeQuestion(Issue issue, Question question) {
        issue.removeQuestion(question);
        if (issue.getQuestions().size() == 0) {
            firebaseAdapter.deleteIssue(issue);
        } else {
            firebaseAdapter.updateIssue(issue);
        }
    }

    public void deleteIssue(Issue issue) {
        firebaseAdapter.deleteIssue(issue);
    }

    public void assignIssue(Administrator admin, Issue issue, Developer dev) {
        checkAdminAndDeveloperExist(admin, dev);
        if (firebaseAdapter.getIssue(issue.getId()) != null) {
            issue.addAssignee(dev);
            firebaseAdapter.updateIssue(issue);
        } else {
            throw new IssueNotFoundException();
        }
    }

    public void unAssignIssue(Administrator admin, Issue issue, Developer dev) {
        checkAdminAndDeveloperExist(admin, dev);
        if (firebaseAdapter.getIssue(issue.getId()) != null) {
            issue.removeAssignee(dev);
            firebaseAdapter.updateIssue(issue);
        } else {
            throw new IssueNotFoundException();
        }
    }

    public void resolveIssue(Developer dev, Issue issue) {
        User developer = firebaseAdapter.getUser(dev.getEmail());
        if (developer == null || developer instanceof Administrator) {
            throw new UserException("Developer not found!");
        }

        if (firebaseAdapter.getIssue(issue.getId()) != null) {
            issue.resolve(dev);
            firebaseAdapter.updateIssue(issue);
        } else {
            throw new IssueNotFoundException();
        }

    }

    private void checkAdminAndDeveloperExist(Administrator admin, Developer dev) {
        User administrator = firebaseAdapter.getUser(admin.getEmail());
        if (administrator == null) {
            throw new UserException("Admin not found!");
        }

        User developer = firebaseAdapter.getUser(dev.getEmail());
        if (developer == null) {
            throw new UserException("Developer not found!");
        }
    }
}
