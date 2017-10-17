package issuetracker.authentication;

import issuetracker.exception.IncorrectPasswordException;
import issuetracker.exception.UserException;
import issuetracker.db.FirebaseAdapter;

import java.util.InvalidPropertiesFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationManager implements IAuthenticationManager {

    public final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private User currentUser;

    private FirebaseAdapter firebaseAdapter;

    public AuthenticationManager(FirebaseAdapter firebaseAdapter) {
        this.firebaseAdapter = firebaseAdapter;
    }

    @Override
    public User login(String email, String password) throws InvalidPropertiesFormatException, InstantiationException {
        isEmailValid(email);

        User retrievedUser = firebaseAdapter.getUser(email);

        if (retrievedUser == null) {
            throw new InstantiationException("User does not exist");
        }

        if (!retrievedUser.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        }

        currentUser = retrievedUser;
        firebaseAdapter.updateLoginStatus(currentUser, true);
        currentUser.setLoggedIn(true);

        return currentUser;
    }

    @Override
    public User addUser(String email, String password) throws InvalidPropertiesFormatException {

        if (this.currentUser instanceof Administrator) {
            isPasswordValid(password);
            isEmailValid(email);
            //TODO: read Firebase to see if email already exists, throw UserException if it doesn't exist
            //TODO: if email doesn't exist, then register user/write to database

            //TODO: ask Xuyun if admins can add admins
            return new Developer(email, password);
        } else {
            throw new UserException("Only administrators can create accounts.");
        }
    }

    @Override
    public boolean logout() {
        if (this.currentUser != null) {
            currentUser.setLoggedIn(false);
            //TODO: reflect change in database i.e. change status to logged out (?)

            return true;
        } else {
            throw new IllegalStateException("No user is logged in.");
        }
    }

    private boolean isEmailValid(String email) throws InvalidPropertiesFormatException {
        Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            throw new InvalidPropertiesFormatException("Invalid email");
        }
    }

    private boolean isPasswordValid(String password) throws InvalidPropertiesFormatException {
        if (password.length() < 8) {
            throw new InvalidPropertiesFormatException("Password is less than length of eight characters.");
        } else {
            return true;
        }
    }

    @Override
    public FirebaseAdapter getDb() {
        return firebaseAdapter;
    }

    @Override
    public void setDb(FirebaseAdapter db) {
        this.firebaseAdapter = db;
    }
}
