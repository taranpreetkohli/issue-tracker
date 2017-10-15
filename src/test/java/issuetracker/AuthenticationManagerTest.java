package issuetracker;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.junit.*;
import org.mockito.Mockito;

public class AuthenticationManagerTest {
    private AuthenticationManager authManager;
    private String newUsername;
    private String newPassword;
    private String newEmail;
    private String existingUsername;
    private String existingEmail;
    private String existingPassword;


    @BeforeClass
    public static void beforeRun() {
    }

    @AfterClass
    public static void afterRun() {
    }

    @Before
    public void setUp() {
        authManager = new AuthenticationManager();

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
        User user = authManager.addUser(newEmail, newPassword);

        //Assert
        Assert.assertEquals(newEmail, user.getEmail());
    }

    @Test
    public void AddUser_ExistingUsername_UserIsNotMadeAndExceptionIsThrown() {
        //Arrange
        //Act
        authManager.addUser(existingEmail, newPassword);

        //Assert
        Assert.fail();
    }

    @Test
    public void AddUser_ValidEmail_UserObjectIsMade() {
        //Arrange
        //Act
        User user = authManager.addUser(newEmail, newPassword);

        //Assert
        Assert.assertEquals(newEmail, user.getEmail());
    }

    @Test(expected = ValueException.class)
    public void AddUser_InvalidEmail_UserIsNotMadeAndExceptionIsThrown() {
        //Arrange
        String invalidEmail = "invalidEmail";

        //Act Assert
        User user = authManager.addUser(invalidEmail, newPassword);
    }

    @Test(expected = RuntimeException.class)
    public void AddUser_DeveloperAccount_UserIsNotCreated() {
        //Arrange
        AuthenticationManager manager = new AuthenticationManager(Mockito.any(Developer.class));

        //Act Assert
        User user = authManager.addUser(newEmail, newPassword);
    }

    @Test
    public void LogIn_ValidCredentials_UserIsLoggedIn() {
        //Arrange

        //Act
        boolean successfulLogin = authManager.login(existingEmail, existingPassword);
        User currentUser = authManager.getCurrentUser();

        //Assert
        Assert.assertTrue(successfulLogin);
        Assert.assertEquals(existingUsername, currentUser.getUsername());
        Assert.assertEquals(existingEmail, currentUser.getEmail());
    }

    @Test
    public void LogIn_InvalidUsernameDoesNotAllowLogin_UserIsUnableToLogin() {
        //Arrange
        String invalidUsername = "invalid";

        //Act
        boolean successfulLogin = authManager.login(invalidUsername, "arbitraryPassword");

        //Assert
        Assert.assertFalse(successfulLogin);
    }

    @Test
    public void LogIn_InvalidPasswordDoesNotAllowLogin_UserIsUnableToLogin() {
        //Arrange
        String wrongPassword = "wrongPassword";

        //Act
        boolean successfulLogin = authManager.login(existingUsername, wrongPassword);

        //Assert
        Assert.assertFalse(successfulLogin);
    }

    @Test
    public void LogOut_UserLoggedIn_UserLogsOut() {
        //Arrange
        //Todo set up logged in state
        //Act
        authManager.logout();
        //Assert
    }

    @Test
    public void LogOut_NoUserLoggedIn_UserLogsOut() {
        //Arrange
        //Act
        authManager.logout();
        //Assert
        Assert.fail();
    }
}
