package issuetracker.authentication;

import issuetracker.db.DBContext;

import java.util.InvalidPropertiesFormatException;

/**
 * IAuthenticationManager is an interface that defines the actions needed to authenticate users the issue tracker system.
 *
 * The authentication manager can be instansiated in many locations safely and therefore does not need to be a singleton.
 * Were this to be a web application there cound be <i>n</i> amount of users "logged in" at any time.
 */
public interface IAuthenticationManager {

    /**
     * Sets the status of a user to logged in and returns an object representing the users functions.
     * A single authentication manager instance can only have 1 user logged in at a time. However, there can be
     * multiple instances of the Authentication manager.
     *
     * @param email assigned to the user account
     * @param password assigned to the user account
     * @return returns the user object with all their issues.
     * @throws InvalidPropertiesFormatException
     * @throws InstantiationException
     */
    User login(String email, String password) throws InvalidPropertiesFormatException, InstantiationException;

    /**
     * Add a new user object to the system.
     * @param email The email assigned to this user
     * @param password The password assigned to this user
     * @return The user object created for convenience
     * @throws InvalidPropertiesFormatException
     */
    User addUser(String email, String password) throws InvalidPropertiesFormatException;

    /**
     * Log the current user of the system out.
     * @return returns true if the logout was successful false otherwise.
     */
    boolean logout();

    /**
     * Get the database
     * @return the database context used by the authentication manager
     */
    DBContext getDb();

    /**
     * Set the database interface for the authentication manager through dependency injection.
     *
     * @param db The DBContext class that will be used by the system.
     * @see DBContext
     */
    void setDb(DBContext db);
}
