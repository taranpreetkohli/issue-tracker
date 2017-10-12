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
    public void AddUser_NewUserUniqueId_EqualsUnique() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AddUser_UserNameSetMakesName_EqualsString() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AddUser_AdministratorUsersAreAdmins_isTrue() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AddUser_SetUserEmailIsSetCorrectly_StringEquals() {
        throw new NotImplementedException("Stub!");
    }

    @Test(expected = ValueException.class)
    public void AddUser_MalformedEmailThrowsException_Exception() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void LogIn_UserLogin_LoginIsTrue() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void LogOut_UserLogout_LoginIsFalse() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void LogIn_InvalidUsernameDoesNotAllowLogin_LoginIsFalse() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void Login_InvalidPasswordDoesNotAllowLogin_LoginIsFalse() {
        throw new NotImplementedException("Stub!");
    }
}
