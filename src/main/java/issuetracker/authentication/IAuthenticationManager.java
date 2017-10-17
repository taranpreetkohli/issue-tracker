package issuetracker.authentication;

public interface IAuthenticationManager {
    User login(String email, String password);
    User addUser(String email, String password);
    boolean logout();
}
