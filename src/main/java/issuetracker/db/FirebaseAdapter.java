package issuetracker.db;

import com.google.firebase.database.DatabaseReference;
import issuetracker.authentication.Administrator;
import issuetracker.authentication.Developer;
import issuetracker.authentication.User;
import issuetracker.clustering.Issue;
import issuetracker.clustering.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FirebaseAdapter {

    private static Logger logger = LoggerFactory.getLogger(FirebaseAdapter.class);

    protected IFirebaseContext db = FirebaseContext.getInstance();

    public FirebaseAdapter registerUser(User newUser){
        // Map user to if they're admin or developer.

        db.write(db.getRoot().child("mappings")
                .child("" + newUser.getEmail().hashCode()), newUser.getClass().getSimpleName());

        // Write the object to the database
        db.write(db.getRoot().child("users")
                .child("" + newUser.getEmail().hashCode()), newUser);

        // Return this object for chaining
        return this;
    }

    public FirebaseAdapter updateLoginStatus(User user, boolean isLoggedin) {
        db.write(db.getRoot().child("users").child(user.getEmail().hashCode() + "").child("loggedIn"), isLoggedin);
        return this;
    }

    public User getUser(String email) {
        // Check mapping for this email
        DatabaseReference mappingRef = db.getRoot()
                .child("mappings")
                .child(email.hashCode() + "");

        String type = db.read(mappingRef, String.class);

        if (type == null || type.trim().isEmpty()) {
            logger.info("No user found with email: " + email);
            return null;
        }

        DatabaseReference userRef = db.getRoot()
                .child("users")
                .child(email.hashCode() + "");

        if (type.equals("Developer")) {
            Developer developer = db.read(userRef, Developer.class);
            logger.info("Retrieved developer with email: " + developer.getEmail());
            return developer;
        } else {
            Administrator administrator = db.read(userRef, Administrator.class);
            logger.info("Retrieved administrator with email: " + administrator.getEmail());
            return administrator;
        }
    }

    public void saveUser(User user) {
        DatabaseReference userRef = db.getRoot()
                .child("users")
                .child("" + user.getEmail().hashCode());

        db.write(userRef, user);
    }

    public Issue getIssue(String issueID) {
        DatabaseReference issuesRef = db.getRoot()
                .child("issues")
                .child(issueID);

        Issue issue = db.read(issuesRef, Issue.class);
        if (issue == null) {
            logger.info("No issue found with id: " + issueID);
        }

        return issue;
    }

    public void saveNewIssue(Issue issue) {
        DatabaseReference issuesRef;
        if (issue.getId() == null) {
            issuesRef = db.getRoot()
                    .child("issues")
                    .push();
            issue.setId(issuesRef.getKey());
        } else {
            issuesRef = db.getRoot()
                    .child("issues")
                    .child(issue.getId());
        }
        db.write(issuesRef, issue);
    }

    public void saveNewQuestion(Question question) {
        DatabaseReference questionsRef;

        if (question.isAssignedToIssue()) {
            questionsRef = db.getRoot().child("questions").child(Long.toString(question.getQuestionID()));
        } else {
            questionsRef = db.getRoot().child("questions").child("unassigned").child(Long.toString(question.getQuestionID()));
        }

        db.write(questionsRef, question);
    }

    public Question getQuestion(String questionID) {
        DatabaseReference questionsRef = db.getRoot()
                .child("questions")
                .child(questionID);

        Question question = db.read(questionsRef, Question.class);
        if (question == null) {
            questionsRef = db.getRoot()
                    .child("questions")
                    .child("unassigned")
                    .child(questionID);

            question = db.read(questionsRef, Question.class);

            if (question == null) {
                logger.info("No question found with id: " + questionID);
            }
        }

        return question;
    }

    public void assignQuestion(String questionID) {
        DatabaseReference questionsRef = db.getRoot().child("questions").child("unassigned").child(questionID);

        Question question = this.getQuestion(questionID);
        if (question == null) {
            logger.warn("No question found");
        } else {
            db.deleteValue(questionsRef);

            question.setAssignedToIssue(true);
            this.saveNewQuestion(question);
        }
    }

    public void unAssignQuestion(String questionID) {
        DatabaseReference questionsRef = db.getRoot().child("questions").child(questionID);

        try {
            Question question = this.getQuestion(questionID);
            db.deleteValue(questionsRef);

            question.setAssignedToIssue(false);
            this.saveNewQuestion(question);
        } catch (NullPointerException e) {
            logger.warn("No question found");
        }
    }

    public void updateIssue(Issue issue) {
        DatabaseReference issuesRef = db.getRoot()
                .child("issues")
                .child(issue.getId());
        db.write(issuesRef, issue);
    }

    public void deleteIssue(Issue issue) {
        DatabaseReference issuesRef = db.getRoot()
                .child("issues")
                .child(issue.getId());
        db.deleteValue(issuesRef);
    }

    public List<Issue> retrieveAllIssues() {
        DatabaseReference issuesRef = db.getRoot()
                .child("issues");
        return db.readChildren(issuesRef, Issue.class);
    }

    public List<Question> retrieveUnassignedQuestions() {
        DatabaseReference questionsRef = db.getRoot()
                .child("questions").child("unassigned");

        return db.readChildren(questionsRef, Question.class);
    }
}
