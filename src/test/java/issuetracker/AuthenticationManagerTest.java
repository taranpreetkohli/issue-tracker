package issuetracker;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.IAuthenticationManager;
import issuetracker.authentication.IUser;
import issuetracker.exception.UserException;
import issuetracker.view.ICommand;
import org.junit.*;

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
        try {
            me = authManager.login("admin@gmail.com", "adminPassword");
        } catch (Exception e) {}
        newEmail = "validEmail@gmail.com";
        newPassword = "P4ssword";

        existingEmail = "existingEmail@gmail.com";
        existingPassword = "ex1stingPassword";
    }

    @After
    public void tearDown() {
    }

    @Test
    public void AddUser_NonExistentUser_UserObjectIsMade() {
        //Arrange
        //Act
        IUser user = null;
        try {
            user = authManager.addUser(newEmail, newPassword);
        } catch (InvalidPropertiesFormatException e) {
        }

        //Assert
        Assert.assertEquals(newEmail, user.getEmail());
    }

    @Test(expected = UserException.class)
    public void AddUser_ExistingUser_UserIsNotMadeAndExceptionIsThrown() throws UserException {
        //Arrange Act Assert
        try {
            authManager.addUser(existingEmail, newPassword);
        } catch (InvalidPropertiesFormatException e) {
        }
    }

    @Test
    public void AddUser_ValidEmail_UserObjectIsMade() {
        //Arrange
        //Act
        IUser user = null;
        try {
            user = authManager.addUser(newEmail, newPassword);
        } catch (InvalidPropertiesFormatException e) {
        }

        //Assert
        Assert.assertEquals(newEmail, user.getEmail());
    }

    @Test(expected = InvalidPropertiesFormatException.class)
    public void AddUser_InvalidEmail_UserIsNotMadeAndExceptionIsThrown() throws InvalidPropertiesFormatException {
        //Arrange
        String invalidEmail = "invalidEmail";

        //Act Assert
        IUser user = authManager.addUser(invalidEmail, newPassword);
    }

    @Test(expected = UserException.class)
    public void AddUser_DeveloperAccount_UserIsNotCreated() throws UserException {
        //Arrange
        IAuthenticationManager manager = new AuthenticationManager();

        //Act Assert
        try {
            manager.login("dev@gmail.com", "devPassword");
            IUser user = authManager.addUser(newEmail, newPassword);
        } catch (Exception e) {}
    }

    @Test
    public void LogIn_ValidCredentials_UserIsLoggedIn() {
        //Arrange
        IUser currentUser = null;

        //Act
        try {
            currentUser = new AuthenticationManager().login(existingEmail, existingPassword);
        } catch (Exception e) {}
        boolean isLoggedIn = currentUser.isLoggedIn();

        //Assert
        Assert.assertTrue(isLoggedIn);
    }

    @Test(expected = InvalidPropertiesFormatException.class)
    public void LogIn_InvalidEmail_UserIsUnableToLogin() throws InvalidPropertiesFormatException {
        //Arrange
        String invalidEmail = "invalid";

        //Act Assert
        IUser user = new AuthenticationManager().login(invalidEmail, "arbitraryPassword");
    }

    @Test
    public void LogIn_IncorrectPassword_UserIsUnableToLogin() {
        //Arrange
        String wrongPassword = "wrongPassword";
        IUser currentUser = null;
        //Act
        try {
            currentUser = new AuthenticationManager().login(existingEmail, wrongPassword);
        } catch (Exception e) {}
        boolean isLoggedIn = currentUser.isLoggedIn();

        //Assert
        Assert.assertFalse(isLoggedIn);
    }


    @Test(expected = InstantiationException.class)
    public void LogIn_UserNotInDatabase_UserIsUnableToLogIn() throws InstantiationException {
        //Arrange
        String noEmail = "MarkaHezawrad@gmail.com";

        //Act Assert
        try {
            new AuthenticationManager().login(noEmail, "shouldntworkanyway");
        } catch (Exception e) {}
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
        IUser currentUser = null;
        try {
            currentUser = new AuthenticationManager().login(existingEmail, existingPassword);
        } catch (Exception e) {}
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
    public void LogOut_NoUserLoggedIn_UserLogsOut() throws IllegalStateException {
        //Arrange
        IAuthenticationManager noUserAuth = new AuthenticationManager();

        //Act Assert
        noUserAuth.logout();
    }
}
