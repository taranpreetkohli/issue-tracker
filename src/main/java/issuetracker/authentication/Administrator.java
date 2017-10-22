package issuetracker.authentication;

import issuetracker.cli.view.ARegisterCommand;
import issuetracker.cli.view.Command;
import issuetracker.cli.view.LogoutCommand;

import java.util.LinkedHashMap;

public class Administrator extends User{

    public Administrator() {
        super();
    }

    public Administrator(String email, String password) {
        super(email, password);
    }


}
