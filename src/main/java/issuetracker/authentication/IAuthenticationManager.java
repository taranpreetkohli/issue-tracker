package issuetracker.authentication;

import issuetracker.db.DBContext;

import java.util.InvalidPropertiesFormatException;

public interface IAuthenticationManager {

    User login(String email, String password) throws InvalidPropertiesFormatException, InstantiationException;
    User addUser(String email, String password) throws InvalidPropertiesFormatException;
    boolean logout();
    void setDb(DBContext db);
    DBContext getDb();
}
