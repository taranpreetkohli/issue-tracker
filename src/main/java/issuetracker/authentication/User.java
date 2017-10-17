package issuetracker.authentication;

import issuetracker.view.ICommand;

import java.util.HashMap;

public abstract class User {
    protected boolean loggedIn;
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public abstract HashMap<String, ICommand> getView();

    public String getEmail() {
        return email;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn){
        this.loggedIn = loggedIn;
    }
}
