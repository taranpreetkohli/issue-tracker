package issuetracker.authentication;

import issuetracker.cli.view.Command;
import issuetracker.cli.view.ICommand;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Administrator extends User{
    private HashMap<String, ICommand> viewMap;

    public Administrator(String email, String password) {
        super(email, password);
        viewMap = new LinkedHashMap<String, ICommand>();
        viewMap.put("R", new Command());
        viewMap.put("V", new Command());
        viewMap.put("M", new Command());
        viewMap.put("L", new Command());
    }

    @Override
    public HashMap<String, ICommand> getView() {
        return this.viewMap;
    }
}
