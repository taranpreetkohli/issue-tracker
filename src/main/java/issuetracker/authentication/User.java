package issuetracker.authentication;

import issuetracker.cli.view.Command;
import issuetracker.cli.view.ICommand;

import java.util.HashMap;
import java.util.Map;

public abstract class User {

    protected boolean loggedIn;
    protected String email;
    protected String password;
    protected Map<String, Command> viewMap;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public Map<String, Command> getViewMap() {
        return viewMap;
    }

    public void setViewMap(Map<String, Command> viewMap) {
        this.viewMap = viewMap;
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
