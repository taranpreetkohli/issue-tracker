package issuetracker;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.junit.*;

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

        newUsername = "newUsername";
        newEmail = "validEmail@gmail.com";
        newPassword = "P4ssword";

        existingUsername = "existingUsername";
        existingEmail = "existingEmail@gmail.com";
        existingPassword = "ex1stingPassword";
    }

    @After
    public void tearDown() {
    }

    @Test
    public void AddUser_NonExistantUsername_UserObjectIsMade() {
        //Arrange
        //Act
        User user = authManager.addUser(newUsername, newPassword, newEmail);

        //Assert
        Assert.assertEquals(newUsername, user.getUsername());
        Assert.assertEquals(newEmail, user.getEmail());
    }

    @Test
    public void AddUser_ExistingUsername_UserIsNotMadeAndExceptionIsThrown() {
        //Arrange
        //Act
        authManager.addUser(existingUsername, newPassword, newEmail);

        //Assert
        Assert.fail();
    }

    @Test
    public void AddUser_ValidEmail_UserObjectIsMade() {
        //Arrange
        //Act
        User user = authManager.addUser(newUsername, newPassword, newEmail);

        //Assert
        Assert.assertEquals(newUsername, user.getUsername());
        Assert.assertEquals(newEmail, user.getEmail());
    }

    @Test(expected = ValueException.class)
    public void AddUser_InvalidEmail_UserIsNotMadeAndExceptionIsThrown() {
        //Arrange
        String invalidEmail = "invalidEmail";

        //Act
        authManager.addUser(newUsername, newPassword, invalidEmail);

        //Assert
    }

    @Test
    public void AddUser_ExistingEmail_UserIsNotMadeAndExceptionIsThrown() {
        //Arrange
        //Act
        authManager.addUser(newUsername, newPassword, existingEmail);

        //Assert
        Assert.fail();
    }

    @Test
    public void LogIn_ValidCredentials_UserIsLoggedIn() {
        //Arrange

        //Act
        boolean successfulLogin = authManager.login(existingUsername, existingPassword);
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
