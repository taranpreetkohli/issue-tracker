package issuetracker;

import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.cli.CLIManager;
import issuetracker.exception.NoInputException;
import org.junit.*;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CLIManagerTest {
    private CLIManager cliManager;
    private AuthenticationManager authenticationManager;
    @BeforeClass
    public static void beforeRun() {
    }

    @AfterClass
    public static void afterRun() {
    }

    @Before
    public void setUp() {
        cliManager = new CLIManager();
        authenticationManager = Mockito.mock(AuthenticationManager.class);
    }

    @After
    public void tearDown(){
        authenticationManager = Mockito.mock(AuthenticationManager.class);
    }

    @Test
    public void CheckUserDetailFormat_ValidFormat_ReturnsTrue() {
        //Arrange
        String validInput = "developer@gmail.com password";

        //Act
        boolean isValid = cliManager.checkUserDetailFormat(validInput);

        //Assert
        assertTrue(isValid) ;
    }

    @Test
    public void CheckUserDetailFormat_NotEnoughInputs_ReturnsFalse() {
        //Arrange
        String invalidInput = "developer@gmail.compassword";
        //Act
        boolean isValid = cliManager.checkUserDetailFormat(invalidInput);

        //Assert
        assertFalse(isValid) ;
    }

    @Test
    public void CheckUserDetailFormat_TooManyInputs_ReturnsFalse() {
        //Arrange
        String invalidInput = "developer@gmail.com password another input";

        //Act
        boolean isValid = cliManager.checkUserDetailFormat(invalidInput);

        //Assert
        assertFalse(isValid) ;
    }

    @Test(expected = NoInputException.class)
    public void CheckUserDetailFormat_NoInput_NoInputExceptionThrown() {
        //Arrange
        String noInput = "";

        //Act Assert
        boolean isValid = cliManager.checkUserDetailFormat(noInput);
    }

    @Test
    public void IsValidCommand_ValidCommand_ReturnsTrue() {
        //Arrange
        String validCommand = "L";
        Mockito.doReturn(new Developer("email@gmail.com", "p4ssword")).when(authenticationManager).getCurrentUser();

        //Act
        boolean isValid = cliManager.isValidCommand(validCommand, authenticationManager.getCurrentUser().getView());

        //Assert
        assertTrue(isValid);
    }

    @Test
    public void IsValidCommand_InvalidCommand_ReturnsFalse() {
        //Arrange
        String invalidCommand = "[zxc]";
        Mockito.doReturn(new Developer("email@gmail.com", "p4ssword")).when(authenticationManager).getCurrentUser();

        //Act
        boolean isValid = cliManager.isValidCommand(invalidCommand, authenticationManager.getCurrentUser().getView());

        //Assert
        assertFalse(isValid);
    }

    @Test(expected = NoInputException.class)
    public void IsValidCommand_NoCommand_NoInputExceptionThrown() {
        //Arrange
        String noCommand = "";
        Mockito.doReturn(new Developer("email@gmail.com", "p4ssword")).when(authenticationManager).getCurrentUser();

        //Act Assert
        boolean isValid = cliManager.isValidCommand(noCommand, authenticationManager.getCurrentUser().getView());
    }

    @Test
    public void StartCLI_ValidLoginFormat_LoginCalled() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void StartCLI_InvalidLoginFormat_LoginNotCalled() {
        //Arrange

        //Act

        //Assert
    }
}
