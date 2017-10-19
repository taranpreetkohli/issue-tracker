package issuetracker.clustering;

import issuetracker.db.FirebaseAdapter;
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
        // add question to cluster
        // update DB
    }

    public void removeQuestion(Issue issue, Question question) {
        // remove question from cluster
        // update DB
    }

    public void deleteIssue(Issue issue) {
        // deletes issue from db
    }
}
