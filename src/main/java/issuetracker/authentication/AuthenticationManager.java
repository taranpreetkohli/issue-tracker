package issuetracker.authentication;

import java.util.InvalidPropertiesFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationManager implements IAuthenticationManager {

    public final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private IUser currentUser;

    @Override
    public IUser login(String email, String password) throws InvalidPropertiesFormatException {
        if (isEmailValid(email)) {
            //TODO: Read Firebase for user that matches email AND password
            currentUser = new Developer(email, password);
            return currentUser;
        } else {
            throw new InvalidPropertiesFormatException("Incorrect email format used.");
        }
    }

    @Override
    public IUser addUser(String email, String password) {
        // TODO ask Xuyun if admins can add admins
        return new Developer(email, password);
    }

    @Override
    public boolean logout() {
        return false;
    }

    private boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
        return matcher.find();
    }
}
