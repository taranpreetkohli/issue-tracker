package issuetracker.authentication;

/**
 * Abstract representation of the User model
 */
public abstract class User {

    /**
     * True if the user is logged in to the system, false otherwise
     */
    protected boolean loggedIn;

    /**
     * Email associated with this User
     */
    protected String email;

    /**
     * Password associated with this User
     */
    protected String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn){
        this.loggedIn = loggedIn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
