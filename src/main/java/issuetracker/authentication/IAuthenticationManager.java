package issuetracker.authentication;

import java.util.InvalidPropertiesFormatException;

public interface IAuthenticationManager {
    IUser login(String email, String password) throws InvalidPropertiesFormatException;
    IUser addUser(String email, String password) throws InvalidPropertiesFormatException;
    boolean logout();
}
