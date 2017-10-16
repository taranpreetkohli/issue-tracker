package issuetracker.authentication;

import issuetracker.view.ICommand;

import javax.annotation.Nullable;
import java.util.Map;

public interface IUser {

    String getEmail();
    boolean isLoggedIn();
    void setLoggedIn(boolean loggedIn);
    Map<String, ICommand> getView();
}
