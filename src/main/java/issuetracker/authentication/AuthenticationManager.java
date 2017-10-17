package issuetracker.authentication;

import issuetracker.db.FirebaseAdapter;

public class AuthenticationManager implements IAuthenticationManager {
    private User currentUser;
    private FirebaseAdapter db;
    public AuthenticationManager(){
        this.db = new FirebaseAdapter();
    }

    @Override
    public User login(String email, String password) {
        currentUser = new Developer(email, password);
        db.updateLoginStatus(currentUser, true);
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
