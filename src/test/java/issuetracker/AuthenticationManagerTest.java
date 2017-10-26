package issuetracker;

import issuetracker.authentication.*;
import issuetracker.db.DBContext;
import issuetracker.exception.IncorrectPasswordException;
import issuetracker.exception.UserException;
import org.junit.*;
import org.mockito.Mockito;

import java.util.InvalidPropertiesFormatException;

public class AuthenticationManagerTest {
    private IAuthenticationManager authManager;
    private String newPassword;
    private String newEmail;
    private String existingEmail;
    private String existingPassword;
    private User me;
    private DBContext db;


    @BeforeClass
    public static void beforeRun() {
    }

    @AfterClass
    public static void afterRun() {
    }

    @Before
    public void setUp() {
        db = Mockito.mock(DBContext.class);
        authManager = new AuthenticationManager(db);
        try {
            Mockito.doReturn(new Administrator("admin@gmail.com", "adminPassword")).when(db).getUser("admin@gmail.com");
            me = authManager.login("admin@gmail.com", "adminPassword");
        } catch (Exception e) {}

        newEmail = "validEmail@gmail.com";
        newPassword = "P4ssword";

        existingEmail = "existingEmail@gmail.com";
        existingPassword = "ex1stingPassword";
    }

    @After
    public void tearDown() {
        db = Mockito.mock(DBContext.class);
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
        IAuthenticationManager devAuthManager = new AuthenticationManager(db);
        Mockito.doReturn(new Developer(existingEmail, existingPassword)).when(db).getUser(existingEmail);

        //Act Assert
        try {
            devAuthManager.login(existingEmail, existingPassword);
            User user = devAuthManager.addUser(newEmail, newPassword);
        } catch (InvalidPropertiesFormatException | InstantiationException e) {}
    }

    @Test
    public void LogIn_ValidCredentials_UserIsLoggedIn() {
        //Arrange
        User currentUser = null;
        Mockito.doReturn(new Developer(existingEmail, existingPassword)).when(db).getUser(existingEmail);

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
        try {
            User user = new AuthenticationManager(db).login(invalidEmail, "arbitraryPassword");
        } catch (InstantiationException e) {}

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
        } catch (InvalidPropertiesFormatException | InstantiationException e) {}
    }


    @Test(expected = InstantiationException.class)
    public void LogIn_UserNotInDatabase_UserIsUnableToLogIn() throws InstantiationException {
        //Arrange
        String noEmail = "MarkaHezawrad@gmail.com";
        Mockito.doReturn(null).when(db).getUser(noEmail);

        //Act Assert
        try {
            new AuthenticationManager(db).login(noEmail, "shouldntworkanyway");
        } catch (InvalidPropertiesFormatException e) {}
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
