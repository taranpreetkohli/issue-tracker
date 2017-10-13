package issuetracker;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.*;

public class AuthenticationManagerTest {
    @BeforeClass
    public static void beforeRun() {
    }

    @AfterClass
    public static void afterRun() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void AddUser_NewUserUniqueId_UserObjectIsMade() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AddUser_UserNameSetMakesName_UserObjectIsMade() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AddUser_AdministratorUsersAreAdmins_UserObjectIsMade() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AddUser_SetUserEmailIsSetCorrectly_UserObjectIsMade() {
        throw new NotImplementedException("Stub!");
    }

    @Test(expected = ValueException.class)
    public void AddUser_MalformedEmail_UserIsNotMadeAndExceptionIsThrown() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void LogIn_UserLogin_UserIsLoggedIn() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void LogOut_UserLogout_UserLogsOut() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void LogIn_InvalidUsernameDoesNotAllowLogin_UserIsUnableToLogin() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void LogIn_InvalidPasswordDoesNotAllowLogin_UserIsUnableToLogin() {
        throw new NotImplementedException("Stub!");
    }
}
