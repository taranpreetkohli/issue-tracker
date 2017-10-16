package issuetracker.authentication;

import issuetracker.view.ICommand;

import java.util.Map;

public interface IUser {

    String getEmail();
    boolean isLoggedIn();
    Map<String, ICommand> getView();
}
