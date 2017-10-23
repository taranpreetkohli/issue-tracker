package issuetracker;


import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.cli.CLIManager;
import issuetracker.clustering.Issue;
import issuetracker.clustering.IssueManager;
import issuetracker.clustering.Question;
import issuetracker.db.FirebaseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) throws IOException {
//        setUpIssuesQuestions();

        new CLIManager(new AuthenticationManager(new FirebaseAdapter()), new IssueManager(new FirebaseAdapter())).loginCLI();
    }

    private static void setUpIssuesQuestions() {
        //Create Questions
        Question question1 = new Question().setQuestionID(500).setQuestion("Question 1").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        Question question2 = new Question().setQuestionID(501).setQuestion("Question 2").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        Question question3 = new Question().setQuestionID(502).setQuestion("Question 3").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        Question question4 = new Question().setQuestionID(503).setQuestion("Question 4").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        question1.setAssignedToIssue(true);
        question2.setAssignedToIssue(true);
        question3.setAssignedToIssue(true);
        question4.setAssignedToIssue(true);
        List<Question> posts1 = new ArrayList<>();
        posts1.add(question1);
        posts1.add(question2);
        posts1.add(question3);
        posts1.add(question4);

        Question question5 = new Question().setQuestionID(504).setQuestion("Question 5").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        Question question6 = new Question().setQuestionID(505).setQuestion("Question 6").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        Question question7 = new Question().setQuestionID(506).setQuestion("Question 7").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        question5.setAssignedToIssue(true);
        question6.setAssignedToIssue(true);
        question7.setAssignedToIssue(true);
        List<Question> posts2 = new ArrayList<>();
        posts2.add(question5);
        posts2.add(question6);
        posts2.add(question7);

        Question question8 = new Question().setQuestionID(507).setQuestion("Question 8").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        Question question9 = new Question().setQuestionID(508).setQuestion("Question 9").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        question8.setAssignedToIssue(true);
        question9.setAssignedToIssue(true);
        List<Question> posts3 = new ArrayList<>();
        posts3.add(question8);
        posts3.add(question9);

        Question question10 = new Question().setQuestionID(509).setQuestion("Question 10").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        question10.setAssignedToIssue(true);
        List<Question> posts4 = new ArrayList<>();
        posts4.add(question10);

        Question question11 = new Question().setQuestionID(510).setQuestion("Question 11").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        question11.setAssignedToIssue(true);
        List<Question> posts5 = new ArrayList<>();
        posts4.add(question11);

        Question question12 = new Question().setQuestionID(511).setQuestion("Question 12").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        question12.setAssignedToIssue(true);
        List<Question> posts6 = new ArrayList<>();
        posts4.add(question12);

        Question question13 = new Question().setQuestionID(512).setQuestion("Question 13").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        Question question14 = new Question().setQuestionID(513).setQuestion("Question 14").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        Question question15 = new Question().setQuestionID(514).setQuestion("Question 15").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        Question question16 = new Question().setQuestionID(515).setQuestion("Question 16").setInformation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempor accumsan eros, non suscipit sapien condimentum interdum. Integer in ultricies urna, ac bibendum mauris. Sed nec dolor porta, malesuada urna et, lobortis mi. ");
        question13.setAssignedToIssue(false);
        question14.setAssignedToIssue(false);
        question15.setAssignedToIssue(false);
        question16.setAssignedToIssue(false);


        //User lists
        List<String> users1 = new ArrayList<>();
        users1.add("user1");

        List<String> users2 = new ArrayList<>();
        users2.add("user1");
        users2.add("user2");

        List<String> users3 = new ArrayList<>();
        users3.add("user1");
        users3.add("user2");
        users3.add("user3");

        List<String> users4 = new ArrayList<>();
        users3.add("user1");
        users3.add("user2");
        users3.add("user3");
        users3.add("user4");

        //Create issues
        Issue issue1 = new Issue().setId("1").setTitle("Issue 1").setSummary("Pellentesque convallis mi eu ex eleifend porta. Donec ut nisl ligula. Nam at leo consequat, feugiat dolor non, laoreet nisi. Vestibulum nec euismod diam, ac cursus tellus. Nunc ut feugiat enim. Vestibulum ultrices faucibus lacinia.").setUsers(users4).setPosts(posts1);
        Issue issue2 = new Issue().setId("2").setTitle("Issue 2").setSummary("Pellentesque convallis mi eu ex eleifend porta. Donec ut nisl ligula. Nam at leo consequat, feugiat dolor non, laoreet nisi. Vestibulum nec euismod diam, ac cursus tellus. Nunc ut feugiat enim. Vestibulum ultrices faucibus lacinia.").setUsers(users3).setPosts(posts2);
        Issue issue3 = new Issue().setId("3").setTitle("Issue 3").setSummary("Pellentesque convallis mi eu ex eleifend porta. Donec ut nisl ligula. Nam at leo consequat, feugiat dolor non, laoreet nisi. Vestibulum nec euismod diam, ac cursus tellus. Nunc ut feugiat enim. Vestibulum ultrices faucibus lacinia.").setUsers(users2).setPosts(posts3);
        Issue issue4 = new Issue().setId("4").setTitle("Issue 4").setSummary("Pellentesque convallis mi eu ex eleifend porta. Donec ut nisl ligula. Nam at leo consequat, feugiat dolor non, laoreet nisi. Vestibulum nec euismod diam, ac cursus tellus. Nunc ut feugiat enim. Vestibulum ultrices faucibus lacinia.").setUsers(users1).setPosts(posts4);
        Issue issue5 = new Issue().setId("5").setTitle("Issue 5").setSummary("Pellentesque convallis mi eu ex eleifend porta. Donec ut nisl ligula. Nam at leo consequat, feugiat dolor non, laoreet nisi. Vestibulum nec euismod diam, ac cursus tellus. Nunc ut feugiat enim. Vestibulum ultrices faucibus lacinia.").setUsers(users1).setPosts(posts5);
        Issue issue6 = new Issue().setId("6").setTitle("Issue 6").setSummary("Pellentesque convallis mi eu ex eleifend porta. Donec ut nisl ligula. Nam at leo consequat, feugiat dolor non, laoreet nisi. Vestibulum nec euismod diam, ac cursus tellus. Nunc ut feugiat enim. Vestibulum ultrices faucibus lacinia.").setUsers(users1).setPosts(posts6);


        FirebaseAdapter firebaseAdapter = new FirebaseAdapter();

        firebaseAdapter.saveNewQuestion(question1);
        firebaseAdapter.saveNewQuestion(question2);
        firebaseAdapter.saveNewQuestion(question3);
        firebaseAdapter.saveNewQuestion(question4);
        firebaseAdapter.saveNewQuestion(question5);
        firebaseAdapter.saveNewQuestion(question6);
        firebaseAdapter.saveNewQuestion(question7);
        firebaseAdapter.saveNewQuestion(question8);
        firebaseAdapter.saveNewQuestion(question9);
        firebaseAdapter.saveNewQuestion(question10);
        firebaseAdapter.saveNewQuestion(question11);
        firebaseAdapter.saveNewQuestion(question12);
        firebaseAdapter.saveNewQuestion(question13);
        firebaseAdapter.saveNewQuestion(question14);
        firebaseAdapter.saveNewQuestion(question15);
        firebaseAdapter.saveNewQuestion(question16);

        firebaseAdapter.saveNewIssue(issue1);
        firebaseAdapter.saveNewIssue(issue2);
        firebaseAdapter.saveNewIssue(issue3);
        firebaseAdapter.saveNewIssue(issue4);
        firebaseAdapter.saveNewIssue(issue5);
        firebaseAdapter.saveNewIssue(issue6);
    }

}
