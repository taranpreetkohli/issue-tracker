package issuetracker;

import com.sun.org.apache.bcel.internal.generic.IUSHR;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.authentication.IAuthenticationManager;
import issuetracker.authentication.IUser;
import issuetracker.view.ICommand;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.omg.CORBA.UserException;

import javax.naming.InvalidNameException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

public class AuthenticationManagerTest {
    private IAuthenticationManager authManager;
    private String newPassword;
    private String newEmail;
    private String existingEmail;
    private String existingPassword;
    private IUser me;


    @BeforeClass
    public static void beforeRun() {
    }

    @AfterClass
    public static void afterRun() {
    }

    @Before
    public void setUp() {
        authManager = new AuthenticationManager();
        //or
        me = authManager.login("admin", "adminPassword");
        newEmail = "validEmail@gmail.com";
        newPassword = "P4ssword";

        existingEmail = "existingEmail@gmail.com";
        existingPassword = "ex1stingPassword";
    }

    @After
    public void tearDown() {
    }

    @Test
    public void AddUser_NonExistentUsername_UserObjectIsMade() {
        //Arrange
        //Act
        IUser user = authManager.addUser(newEmail, newPassword);

        //Assert
        Assert.assertEquals(newEmail, user.getEmail());
    }

    @Test(expected = UserException.class)
    public void AddUser_ExistingUsername_UserIsNotMadeAndExceptionIsThrown() {
        //Arrange Act Assert
        authManager.addUser(existingEmail, newPassword);
    }

    @Test
    public void AddUser_ValidEmail_UserObjectIsMade() {
        //Arrange
        //Act
        IUser user = authManager.addUser(newEmail, newPassword);

        //Assert
        Assert.assertEquals(newEmail, user.getEmail());
    }

    @Test(expected = InvalidPropertiesFormatException.class)
    public void AddUser_InvalidEmail_UserIsNotMadeAndExceptionIsThrown() {
        //Arrange
        String invalidEmail = "invalidEmail";

        //Act Assert
        IUser user = authManager.addUser(invalidEmail, newPassword);
    }

    @Test(expected = UserException.class)
    public void AddUser_DeveloperAccount_UserIsNotCreated() {
        //Arrange
        IAuthenticationManager manager = new AuthenticationManager();

        //Act Assert
        manager.login("dev", "devPassword");
        IUser user = authManager.addUser(newEmail, newPassword);
    }

    @Test
    public void LogIn_ValidCredentials_UserIsLoggedIn() {
        //Arrange
        //Act
        IUser currentUser = new AuthenticationManager().login(existingEmail, existingPassword);
        boolean isLoggedIn = currentUser.isLoggedIn();

        //Assert
        Assert.assertTrue(isLoggedIn);
    }

    @Test(expected = InvalidPropertiesFormatException.class)
    public void LogIn_InvalidEmail_UserIsUnableToLogin() {
        //Arrange
        String invalidEmail = "invalid";

        //Act Assert
        IUser user = new AuthenticationManager().login(invalidEmail, "arbitraryPassword");
    }

    @Test
    public void LogIn_IncorrectPassword_UserIsUnableToLogin() {
        //Arrange
        String wrongPassword = "wrongPassword";

        //Act
        IUser currentUser = new AuthenticationManager().login(existingEmail, wrongPassword);
        boolean isLoggedIn = currentUser.isLoggedIn();

        //Assert
        Assert.assertFalse(isLoggedIn);
    }


    @Test(expected = InstantiationException.class)
    public void LogIn_UserNotInDatabase_UserIsUnableToLogIn() {
        //Arrange
        String noEmail = "MarkaHezawrad@gmail.com";

        //Act Assert
        new AuthenticationManager().login(noEmail, "shouldntworkanyway");
    }

    @Test
    public void LogIn_AdminAccount_UserIsShownAdminView() {
        //Arrange
        String[] expected = new String[] {
                "R", "V", "M", "L"
        };

        //Act
        Map<String, ICommand> commands = me.getView();

        //Assert
        Assert.assertArrayEquals(expected, commands.keySet().toArray());

    }

    @Test
    public void LogIn_DeveloperAccount_UserIsShownDeveloperView() {
        //Arrange
        IUser currentUser = new AuthenticationManager().login(existingEmail, existingPassword);
        String[] expected = new String[] {
                "V", "M", "L"
        };

        //Act
        Map<String, ICommand> commands = currentUser.getView();

        //Assert
        Assert.assertArrayEquals(expected, commands.keySet().toArray());
    }


    @Test
    public void LogOut_UserLoggedIn_UserLogsOut() {
        //Arrange Act
        boolean loggedOut = authManager.logout();

        //Assert
        Assert.assertTrue(loggedOut);
    }

    @Test(expected = IllegalStateException.class)
    public void LogOut_NoUserLoggedIn_UserLogsOut() {
        //Arrange
        IAuthenticationManager noUserAuth = new AuthenticationManager();

        //Act Assert
        noUserAuth.logout();
    }
}
