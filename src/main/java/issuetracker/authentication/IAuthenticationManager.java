package issuetracker.authentication;

import issuetracker.db.FirebaseAdapter;

import java.util.InvalidPropertiesFormatException;

public interface IAuthenticationManager {

    User login(String email, String password) throws InvalidPropertiesFormatException;
    User addUser(String email, String password) throws InvalidPropertiesFormatException;
    boolean logout();
    void setDb(FirebaseAdapter db);
    FirebaseAdapter getDb();
}
