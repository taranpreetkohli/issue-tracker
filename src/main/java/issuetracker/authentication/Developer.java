package issuetracker.authentication;

import issuetracker.cli.view.Command;
import issuetracker.cli.view.ICommand;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Developer extends User {

    private HashMap<String, ICommand> viewMap;

    public Developer() {
        super();
    }

    public Developer(String email, String password) {
        super(email, password);

        viewMap = new LinkedHashMap<String, ICommand>();
        viewMap.put("V", new Command());
        viewMap.put("M", new Command());
        viewMap.put("L", new Command());
    }

    @Override
    public HashMap<String, ICommand> getView() {
        return this.viewMap;
    }
}
