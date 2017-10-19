package issuetracker.authentication;

import issuetracker.cli.view.ICommand;

import java.util.HashMap;
import java.util.Map;

public abstract class User {

    protected boolean loggedIn;
    private String email;
    private String password;
    protected Map<String, ICommand> viewMap;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public Map<String, ICommand> getView() {
        return viewMap;
    }

    public void setView(Map<String, ICommand> viewMap) {
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
