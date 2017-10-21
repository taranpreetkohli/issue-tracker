package issuetracker.authentication;

import issuetracker.cli.view.ARegisterCommand;
import issuetracker.cli.view.Command;
import issuetracker.cli.view.LogoutCommand;

import java.util.LinkedHashMap;

public class Administrator extends User{

    public Administrator() {
        super();
        viewMap = new LinkedHashMap<String, Command>();
        viewMap.put("R", new Command());
        viewMap.put("V", new Command());
        viewMap.put("M", new Command());
        viewMap.put("L", new Command());
    }

    public Administrator(String email, String password) {
        super(email, password);
        viewMap = new LinkedHashMap<String, Command>();
        viewMap.put("R", new ARegisterCommand());
        viewMap.put("V", new Command());
        viewMap.put("M", new Command());
        viewMap.put("L", new LogoutCommand());
    }


}
