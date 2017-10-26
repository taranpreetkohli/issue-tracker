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

/**
 * Implementation agnostic database access class. Calls to this class should not have to worry about the database
 * implementation.
 */
public class FirebaseAdapter {

    private static Logger logger = LoggerFactory.getLogger(FirebaseAdapter.class);

    // Using Firebase as our database
    protected IFirebaseContext db = FirebaseContext.getInstance();

    /**
     * Place a new user object in the database
     *
     * @param newUser User object to be wrote to database
     * @return Returns this object for ease of use. Following "Method Chaining" design pattern.
     */
    public FirebaseAdapter registerUser(User newUser) {
        // Map user to if they're admin or developer.

        db.write(db.getRoot().child("mappings")
                .child("" + newUser.getEmail().hashCode()), newUser.getClass().getSimpleName());

        // Write the object to the database
        db.write(db.getRoot().child("users")
                .child("" + newUser.getEmail().hashCode()), newUser);

        // Return this object for chaining
        return this;
    }

    /**
     * Following Firebases realtime database model a user logging in on our system will propigate to firebase so that
     * we can see that they're logged in on the entire system.
     *
     * @param user       The user object
     * @param isLoggedin Status of their login
     * @return Returns this object for ease of use. Following "Method Chaining" design pattern.
     */
    public FirebaseAdapter updateLoginStatus(User user, boolean isLoggedin) {
        db.write(db.getRoot().child("users").child(user.getEmail().hashCode() + "").child("loggedIn"), isLoggedin);
        return this;
    }

    /**
     * Get a user corresponding to this email.
     *
     * @param email Email of the user
     * @return User object representing the actions and information related to this user
     */
    public User getUser(String email) {
        // Check mapping for this email using the hashcode of this email address as the id
        DatabaseReference mappingRef = db.getRoot()
                .child("mappings")
                .child(email.hashCode() + "");

        String type = db.read(mappingRef, String.class);

        if (type == null || type.trim().isEmpty()) {
            logger.info("No user found with email: " + email);
            return null;
        }

        // Once we've discovered weather this user is an administrator or developer we can then type the user to a
        // developer or admin.
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

    /**
     * Save a new user to the database
     *
     * @param user User to save.
     */
    public void saveUser(User user) {
        DatabaseReference userRef = db.getRoot()
                .child("users")
                .child("" + user.getEmail().hashCode());

        db.write(userRef, user);
    }


    /**
     * Retrieve an issue from the database using the id
     *
     * @param issueID Unique ID of the issue.
     * @return <code>Issue</code> object retrieved from the database.
     */
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

    /**
     * Save a new <code>Issue</code> object to the database.
     *
     * @param issue Issue object to save to database.
     */
    public void saveNewIssue(Issue issue) {
        DatabaseReference issuesRef;
        if (issue.getId() == null) {
            // If an issue has no id then make a new issue object.
            issuesRef = db.getRoot()
                    .child("issues")
                    .push();
            issue.setId(issuesRef.getKey());
        } else {
            // If an issue object has the same id rewrite it.
            issuesRef = db.getRoot()
                    .child("issues")
                    .child(issue.getId());
        }
        db.write(issuesRef, issue);
    }

    /**
     * Save a question (Forum post) to the database.
     *
     * @param question <code>Question</code> object relating to a forum post.
     */
    public void saveNewQuestion(Question question) {
        DatabaseReference questionsRef;

        if (question.isAssignedToIssue()) {
            questionsRef = db.getRoot().child("questions").child(Long.toString(question.getQuestionID()));
        } else {
            questionsRef = db.getRoot().child("questions").child("unassigned").child(Long.toString(question.getQuestionID()));
        }

        db.write(questionsRef, question);
    }

    /**
     * Get a question (forum post) from the database.
     *
     * @param questionID Unique ID pertaining to the question
     * @return <code>Question</code> constructed from the database information.
     */
    public Question getQuestion(String questionID) {
        DatabaseReference questionsRef = db.getRoot()
                .child("questions")
                .child(questionID);

        // Try and get a question from the parent label "Questions".
        Question question = db.read(questionsRef, Question.class);
        // If there is no question in this parent object it must be in the "Unassigned" child label.
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

    /**
     * Mark a question as assigned.
     *
     * @param questionID the question id to be assigned.
     */
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

    /**
     * Mark a question as unassigned.
     *
     * @param questionID the id of a question to be marked.
     */
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

    /**
     * Update an issue in the database to the version currently in the system.
     *
     * @param issue Issue to be updated
     */
    public void updateIssue(Issue issue) {
        DatabaseReference issuesRef = db.getRoot()
                .child("issues")
                .child(issue.getId());
        db.write(issuesRef, issue);
    }

    /**
     * Remove an issue from the database.
     *
     * @param issue Issue to be removed.
     */
    public void deleteIssue(Issue issue) {
        DatabaseReference issuesRef = db.getRoot()
                .child("issues")
                .child(issue.getId());
        db.deleteValue(issuesRef);
    }

    /**
     * Get all the issues in the system.
     *
     * @return List of all issues in the system.
     */
    public List<Issue> retrieveAllIssues() {
        DatabaseReference issuesRef = db.getRoot()
                .child("issues");
        return db.readChildren(issuesRef, Issue.class);
    }

    /**
     * Get all unassigned issues in the  system
     *
     * @return List of all unassigned issues in the system.
     */
    public List<Question> retrieveUnassignedQuestions() {
        DatabaseReference questionsRef = db.getRoot()
                .child("questions").child("unassigned");

        return db.readChildren(questionsRef, Question.class);
    }
}
