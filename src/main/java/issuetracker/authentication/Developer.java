package issuetracker.authentication;

import issuetracker.view.ICommand;

import java.util.HashMap;

public class Developer extends User {
    public Developer(String email, String password) {
        super(email, password);
    }

    @Override
    public HashMap<String, ICommand> getView() {
        return null;
    }
}
