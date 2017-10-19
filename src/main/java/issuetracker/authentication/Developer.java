package issuetracker.authentication;

import issuetracker.cli.view.Command;

import java.util.LinkedHashMap;

public class Developer extends User {

    public Developer() {
        super();
    }

    public Developer(String email, String password) {
        super(email, password);

        viewMap = new LinkedHashMap<String, Command>();
        viewMap.put("V", new Command());
        viewMap.put("M", new Command());
        viewMap.put("L", new Command());
    }

}
