package issuetracker;

import issuetracker.clustering.IssueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Runner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) throws IOException {
        IssueManager m = new IssueManager();
    }

}
