package issuetracker.authentication;

import issuetracker.view.Command;
import issuetracker.view.ICommand;

import java.util.HashMap;

public class Developer extends User {

    private HashMap<String, ICommand> viewMap;

    public Developer() {
        super();
    }

    public Developer(String email, String password) {
        super(email, password);

        viewMap = new HashMap<String, ICommand>();
        viewMap.put("V", new Command());
        viewMap.put("M", new Command());
        viewMap.put("L", new Command());
    }

    @Override
    public HashMap<String, ICommand> getView() {
        return this.viewMap;
    }
}
