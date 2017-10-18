package issuetracker;

import issuetracker.cli.CLIManager;
import issuetracker.exception.NoInputException;
import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CLIManagerTest {
    private CLIManager cliManager;
    @BeforeClass
    public static void beforeRun() {
    }

    @AfterClass
    public static void afterRun() {
    }

    @Before
    public void setUp() {
        cliManager = new CLIManager();
    }

    @After
    public void tearDown(){
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

        //Act
        boolean isValid = cliManager.isValidCommand(validCommand);

        //Assert
        assertTrue(isValid);
    }

    @Test
    public void IsValidCommand_InvalidCommand_ReturnsFalse() {
        //Arrange
        String invalidCommand = "[zxc]";

        //Act
        boolean isValid = cliManager.isValidCommand(invalidCommand);

        //Assert
        assertFalse(isValid);
    }

    @Test(expected = NoInputException.class)
    public void IsValidCommand_NoCommand_NoInputExceptionThrown() {
        //Arrange
        String noCommand = "";

        //Act Assert
        boolean isValid = cliManager.isValidCommand(noCommand);
    }
}
