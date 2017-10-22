package issuetracker;


import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.cli.CLIManager;
import issuetracker.clustering.Issue;
import issuetracker.clustering.IssueManager;
import issuetracker.db.FirebaseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) throws IOException {

//        List<String> users1 = new ArrayList<>();
//        users1.add("user1");
//
//        List<String> users2 = new ArrayList<>();
//        users2.add("user1");
//        users2.add("user2");
//
//        List<String> users3 = new ArrayList<>();
//        users3.add("user1");
//        users3.add("user2");
//        users3.add("user3");
//
//        Issue issue1 = new Issue();
//        issue1.setId("1").setTitle("Priority 1").setUsers(users3);
//
//        Issue issue2 = new Issue();
//        issue2.setId("2").setTitle("Priority 2").setUsers(users2);
//
//        Issue issue3 = new Issue();
//        issue3.setId("3").setTitle("Priority 3").setUsers(users1);
//
//        FirebaseAdapter firebaseAdapter = new FirebaseAdapter();
//
//        firebaseAdapter.saveNewIssue(issue1);
//        firebaseAdapter.saveNewIssue(issue2);
//        firebaseAdapter.saveNewIssue(issue3);

//
//        Administrator administrator = (Administrator) firebaseAdapter.getUser("admin@gmail.com");
//        Developer developer = (Developer) firebaseAdapter.getUser("dev1@gmail.com");
//
//        IssueManager issueManager = new IssueManager(firebaseAdapter);
//
//        Issue issue1 = firebaseAdapter.getIssue("1");
//        Issue issue2 = firebaseAdapter.getIssue("2");
//        Issue issue3 = firebaseAdapter.getIssue("3");
//
//        logger.error("assigning 1");
//        issueManager.assignIssue(administrator, issue1, developer);
//        logger.error("assigning 2");
//        issueManager.assignIssue(administrator, issue2, developer);
//        logger.error("assigning 3");
//        issueManager.assignIssue(administrator, issue3, developer);

        new CLIManager(new AuthenticationManager(new FirebaseAdapter()), new IssueManager(new FirebaseAdapter())).loginCLI();
    }

}
