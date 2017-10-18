package issuetracker;

import issuetracker.cli.CLIManager;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.*;

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
    public void CheckInputFormat_ValidInput_ReturnsTrue() {
        //Arrange

        //Act

        //Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void CheckInputFormat_InvalidInput_ReturnsFalse() {
        //Arrange

        //Act

        //Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void CheckInputFormat_NoInput_NoInputExceptionThrown() {
        //Arrange

        //Act

        //Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void IsValidCommand_ValidCommand_ReturnsTrue() {
        //Arrange

        //Act

        //Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void IsValidCommand_InvalidFormat_ReturnsFalse() {
        //Arrange

        //Act

        //Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void IsValidCommand_NoCommand_NoInputExceptionThrown() {
        //Arrange

        //Act

        //Assert
        throw new NotImplementedException("Stub!");
    }
}
