package issuetracker;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.authentication.IAuthenticationManager;
import issuetracker.authentication.User;
import issuetracker.db.FirebaseAdapter;
import issuetracker.exception.IncorrectPasswordException;
import issuetracker.exception.UserException;
import issuetracker.view.ICommand;
import org.junit.*;
import org.mockito.Mockito;

import java.util.InvalidPropertiesFormatException;
import java.util.Map;

public class AuthenticationManagerTest {
    private IAuthenticationManager authManager;
    private String newPassword;
    private String newEmail;
    private String existingEmail;
    private String existingPassword;
    private User me;
    private FirebaseAdapter db;


    @BeforeClass
    public static void beforeRun() {
    }

    @AfterClass
    public static void afterRun() {
    }

    @Before
    public void setUp() {
        db = Mockito.mock(FirebaseAdapter.class);
        authManager = new AuthenticationManager(db);
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
        db = Mockito.mock(FirebaseAdapter.class);
    }

    @Test
    public void AddUser_NonExistentUser_UserObjectIsMade() {
        //Arrange
        //Act
        User user = null;
        try {
            user = authManager.addUser(newEmail, newPassword);
        } catch (InvalidPropertiesFormatException e) {
        }

        //Assert
        Assert.assertEquals(newEmail, user.getEmail());
    }

    @Test(expected = UserException.class)
    public void AddUser_ExistingUser_UserIsNotMadeAndExceptionIsThrown() throws UserException {
        //Arrange
        Mockito.doReturn(new Developer(existingEmail, existingPassword)).when(db).getUser(existingEmail);
        // Act Assert
        try {
            authManager.addUser(existingEmail, newPassword);
        } catch (InvalidPropertiesFormatException e) {
        }
    }

    @Test
    public void AddUser_ValidEmail_UserObjectIsMade() {
        //Arrange
        //Act
        User user = null;
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
        User user = authManager.addUser(invalidEmail, newPassword);
    }

    @Test(expected = UserException.class)
    public void AddUser_DeveloperAccount_UserIsNotCreated() throws UserException {
        //Arrange
        IAuthenticationManager manager = new AuthenticationManager(db);

        //Act Assert
        try {
            manager.login("dev@gmail.com", "devPassword");
            User user = authManager.addUser(newEmail, newPassword);
        } catch (Exception e) {}
    }

    @Test
    public void LogIn_ValidCredentials_UserIsLoggedIn() {
        //Arrange
        User currentUser = null;

        //Act
        try {
            currentUser = new AuthenticationManager(db).login(existingEmail, existingPassword);
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
        User user = new AuthenticationManager(db).login(invalidEmail, "arbitraryPassword");
    }

    @Test(expected = IncorrectPasswordException.class)
    public void LogIn_IncorrectPassword_UserIsUnableToLogin() {
        //Arrange
        String wrongPassword = "wrongPassword";
        Mockito.doReturn(new Developer(existingEmail, existingPassword)).when(db).getUser(existingEmail);
        User currentUser = null;
        //Act
        try {
            currentUser = new AuthenticationManager(db).login(existingEmail, wrongPassword);
        } catch (Exception e) {}
    }


    @Test(expected = InstantiationException.class)
    public void LogIn_UserNotInDatabase_UserIsUnableToLogIn() throws InstantiationException {
        //Arrange
        String noEmail = "MarkaHezawrad@gmail.com";
        Mockito.doReturn(null).when(db).getUser(noEmail);

        //Act Assert
        try {
            new AuthenticationManager(db).login(noEmail, "shouldntworkanyway");
        } catch (Exception e) {}
    }

    @Test
    public void LogIn_AdminAccount_UserIsShownAdminView() {
        //Arrange Act
        Map<String, ICommand> commands = me.getView();

        //Assert
        Assert.assertTrue(commands.containsKey("R"));
        Assert.assertTrue(commands.containsKey("V"));
        Assert.assertTrue(commands.containsKey("M"));
        Assert.assertTrue(commands.containsKey("L"));
    }

    @Test
    public void LogIn_DeveloperAccount_UserIsShownDeveloperView() {
        //Arrange
        User currentUser = null;
        try {
            currentUser = new AuthenticationManager(db).login(existingEmail, existingPassword);
        } catch (Exception e) {}

        //Act
        Map<String, ICommand> commands = currentUser.getView();

        //Assert
        Assert.assertFalse(commands.containsKey("R"));
        Assert.assertTrue(commands.containsKey("V"));
        Assert.assertTrue(commands.containsKey("M"));
        Assert.assertTrue(commands.containsKey("L"));
    }


    @Test
    public void LogOut_UserLoggedIn_UserLogsOut() {
        //Arrange Act
        boolean loggedOut = authManager.logout();

        //Assert
        Assert.assertTrue(loggedOut);
    }

    @Test(expected = IllegalStateException.class)
    public void LogOut_NoUserLoggedIn_ExceptionThrown() throws IllegalStateException {
        //Arrange
        IAuthenticationManager noUserAuth = new AuthenticationManager(db);

        //Act Assert
        noUserAuth.logout();
    }
}
