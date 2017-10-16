package issuetracker.authentication;

import issuetracker.view.ICommand;

import java.util.HashMap;
import java.util.regex.Pattern;

public abstract class User implements IUser {

    protected boolean loggedIn;
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public abstract HashMap<String, ICommand> getView();

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public void setLoggedIn(boolean loggedIn){
        this.loggedIn = loggedIn;
    }
}
