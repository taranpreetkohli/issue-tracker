package issuetracker.authentication;

import issuetracker.db.DBContext;
import issuetracker.exception.IncorrectPasswordException;
import issuetracker.exception.UserException;

import java.util.InvalidPropertiesFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles Authentication of users.
 */
public class AuthenticationManager implements IAuthenticationManager {

    public final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private User currentUser;

    private DBContext dBContext;

    /**
     * Authentication manager is responsible for handling the creation of User objects and child classes.
     *
     * @param dBContext The Database context currently used by the system
     * @see User
     * @see Developer
     * @see Administrator
     */
    public AuthenticationManager(DBContext dBContext) {
        this.dBContext = dBContext;
    }

    /**
     * Allows a user to login
     *
     * @param email    assigned to the user account
     * @param password assigned to the user account
     * @return User model representing the logged-in user
     * @throws InvalidPropertiesFormatException
     * @throws InstantiationException
     */
    @Override
    public User login(String email, String password) throws InvalidPropertiesFormatException, InstantiationException {
        isEmailValid(email);

        User retrievedUser = dBContext.getUser(email);

        if (retrievedUser == null) {
            throw new InstantiationException("User does not exist");
        }

        if (!retrievedUser.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        }

        currentUser = retrievedUser;
        dBContext.updateLoginStatus(currentUser, true);
        currentUser.setLoggedIn(true);

        return currentUser;
    }

    /**
     * Allows an administrator to add a new developer to the database
     * @param email The email assigned to this user
     * @param password The password assigned to this user
     * @return User model representing the newly created Developer
     * @throws InvalidPropertiesFormatException
     */
    @Override
    public User addUser(String email, String password) throws InvalidPropertiesFormatException {

        if (this.currentUser instanceof Administrator) {
            isPasswordValid(password);
            isEmailValid(email);

            if (dBContext.getUser(email) != null) {
                throw new UserException("User already exists");
            }

            User newUser = new Developer(email, password);
            dBContext.registerUser(newUser);

            return newUser;
        } else {
            throw new UserException("Only administrators can create accounts.");
        }
    }

    /**
     * Allows a user to logout from the system
     * @return true if operation is successful
     */
    @Override
    public boolean logout() {
        if (currentUser != null) {
            String email = currentUser.getEmail();
            dBContext.updateLoginStatus(dBContext.getUser(email), false);
            currentUser.setLoggedIn(false);
            currentUser = null;

            return true;
        } else {
            throw new IllegalStateException("No user is logged in.");
        }
    }

    /**
     * Ensure that an email address that was entered is actually in an email format.
     *
     * @param email email passed to the Authentication Manager
     * @return true if the email passes, throws an exception otherwise
     * @throws InvalidPropertiesFormatException
     */
    private boolean isEmailValid(String email) throws InvalidPropertiesFormatException {
        Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            throw new InvalidPropertiesFormatException("Invalid email");
        }
    }

    /**
     * Ensure that the password given follows a convention. In this case more than 8 characters.
     *
     * @param password the password given in by the local systme
     * @return true if password is valid
     * @throws InvalidPropertiesFormatException
     */
    private boolean isPasswordValid(String password) throws InvalidPropertiesFormatException {
        if (password.length() < 8) {
            throw new InvalidPropertiesFormatException("Password is less than length of eight characters.");
        } else {
            return true;
        }
    }

    /**
     * Get the user who is currently <i>Logged in</i> to the AuthenticationManager and is there fore the user accessing
     * the system.
     *
     * @return
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public DBContext getDb() {
        return dBContext;
    }

    @Override
    public void setDb(DBContext db) {
        this.dBContext = db;
    }
}
