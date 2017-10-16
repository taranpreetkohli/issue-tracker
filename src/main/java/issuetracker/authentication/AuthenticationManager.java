package issuetracker.authentication;

import issuetracker.exception.UserException;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.InvalidPropertiesFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationManager implements IAuthenticationManager {

    public final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private IUser currentUser;

    @Override
    public IUser login(String email, String password) throws InvalidPropertiesFormatException {
        isEmailValid(email);

        //TODO: Read Firebase for user that matches email AND password
        currentUser = new Administrator(email, password);
        return currentUser;
    }

    @Override
    public IUser addUser(String email, String password) throws InvalidPropertiesFormatException {

        if (this.currentUser instanceof Administrator) {
            isPasswordValid(password);
            isEmailValid(email);
            //TODO: read Firebase to see if email already exists
            //TODO: if email doesn't exist, then register user/write to database

            //TODO: ask Xuyun if admins can add admins
            return new Developer(email, password);
        } else {
            throw new UserException("Only administrators can create accounts.");
        }
    }

    @Override
    public boolean logout() {
        return false;
    }

    private boolean isEmailValid(String email) throws InvalidPropertiesFormatException {
        Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            throw new InvalidPropertiesFormatException("Invalid email");
        }
    }

    private boolean isPasswordValid(String password) throws InvalidPropertiesFormatException {
        if (password.length() < 8) {
            throw new InvalidPropertiesFormatException("Password is less than length of eight characters.");
        } else {
            return true;
        }
    }
}
