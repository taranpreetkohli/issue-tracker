package issuetracker.db;

import com.google.firebase.database.DatabaseReference;
import issuetracker.authentication.Administrator;
import issuetracker.authentication.Developer;
import issuetracker.authentication.IUser;

import java.security.CryptoPrimitive;

public class FirebaseAdapter {
    protected IFirebaseContext db = FirebaseContext.getInstance();

    public FirebaseAdapter registerUser(IUser newUser){
        // Map user to if they're admin or developer.

        db.write(db.getRoot().child("mappings")
                .child("" + newUser.getEmail().hashCode()), newUser.getClass().getSimpleName());

        // Write the object to the database
        db.write(db.getRoot().child("users")
                .child("" + newUser.getEmail().hashCode()), newUser);

        // Return this object for chaining
        return this;
    }

    public IUser getUser(String email){
        // Check mapping for this email
        final IUser[] user = new IUser[1];

        DatabaseReference mappingRef = db.getRoot()
                .child("mappings")
                .child(email.hashCode() + "");

        db.read(mappingRef, String.class, value -> {
            if (value == null || value.trim().isEmpty()) {
                user[0] = null;
                return;
            }

            DatabaseReference userRef = db.getRoot()
                    .child("users")
                    .child(email.hashCode() + "");

            if (value.equals("Developer")) {
                db.read(userRef, Developer.class, developer -> user[0] = developer);
            } else {
                db.read(userRef, Administrator.class, admin -> user[0] = admin);
            }
        });

        return user[0];
    }
}
