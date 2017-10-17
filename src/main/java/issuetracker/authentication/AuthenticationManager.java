package issuetracker.authentication;

public class AuthenticationManager implements IAuthenticationManager {
    private User currentUser;

    @Override
    public User login(String email, String password) {
        currentUser = new Developer(email, password);
        return currentUser;
    }

    @Override
    public User addUser(String email, String password) {
        // TODO ask Xuyun if admins can add admins
        return new Developer(email, password);
    }

    @Override
    public boolean logout() {
        return false;
    }
}
