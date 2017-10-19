package issuetracker.db;

import com.google.firebase.database.DatabaseReference;
import issuetracker.authentication.Administrator;
import issuetracker.authentication.Developer;
import issuetracker.authentication.User;
import issuetracker.clustering.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void updateIssue(Issue issue) {
        // saves the issue in the database
    }

    public void deleteIssue(Issue issue) {
        // deletes the issue from the database
    }
}
