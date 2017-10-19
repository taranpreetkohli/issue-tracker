package issuetracker.authentication;

import issuetracker.cli.view.Command;

import java.util.LinkedHashMap;

public class Administrator extends User{

    public Administrator() {
        super();
    }

    public Administrator(String email, String password) {
        super(email, password);
        viewMap = new LinkedHashMap<String, Command>();
        viewMap.put("R", new Command());
        viewMap.put("V", new Command());
        viewMap.put("M", new Command());
        viewMap.put("L", new Command());
    }


}
