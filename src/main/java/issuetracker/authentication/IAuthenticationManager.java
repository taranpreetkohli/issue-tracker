package issuetracker.authentication;

public interface IAuthenticationManager {
    IUser login(String email, String password);
    IUser addUser(String email, String password);
    boolean logout();
}
