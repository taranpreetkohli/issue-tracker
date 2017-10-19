package issuetracker;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.cli.CLIManager;
import issuetracker.clustering.ClusterManager;
import issuetracker.exception.NoInputException;
import org.junit.*;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.booleanThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CLIManagerTest {
    private CLIManager cliManager;
    private AuthenticationManager authenticationManager;
    private ClusterManager clusterManager;
    @BeforeClass
    public static void beforeRun() {
    }

    @AfterClass
    public static void afterRun() {
    }

    @Before
    public void setUp() {
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        clusterManager = Mockito.mock(ClusterManager.class);
        cliManager = new CLIManager(authenticationManager, clusterManager);
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
        boolean isValid = cliManager.isValidCommand(validCommand);

        //Assert
        assertTrue(isValid);
    }

    @Test
    public void IsValidCommand_InvalidCommand_ReturnsFalse() {
        //Arrange
        String invalidCommand = "[zxc]";
        Mockito.doReturn(new Developer("email@gmail.com", "p4ssword")).when(authenticationManager).getCurrentUser();

        //Act
        boolean isValid = cliManager.isValidCommand(invalidCommand);

        //Assert
        assertFalse(isValid);
    }

    @Test(expected = NoInputException.class)
    public void IsValidCommand_NoCommand_NoInputExceptionThrown() {
        //Arrange
        String noCommand = "";
        Mockito.doReturn(new Developer("email@gmail.com", "p4ssword")).when(authenticationManager).getCurrentUser();

        //Act Assert
        boolean isValid = cliManager.isValidCommand(noCommand);
    }

    @Test
    public void IsValidCommand_RegisterUserUsingDeveloperAccount_ReturnsFalse() {
        //Arrange
        String registerCommand = "R";
        Mockito.doReturn(new Developer("email@gmail.com", "p4ssword")).when(authenticationManager).getCurrentUser();

        //Act
        boolean isValid = cliManager.isValidCommand(registerCommand);

        //Assert
        assertFalse(isValid);
    }

    @Test
    public void IsValidCommand_LowerCaseValidCommand_ReturnsTrue() {
        //Arrange
        String lowerCaseCommand = "l";
        Mockito.doReturn(new Developer("email@gmail.com", "p4ssword")).when(authenticationManager).getCurrentUser();

        //Act
        boolean isValid = cliManager.isValidCommand(lowerCaseCommand);

        //Assert
        assertTrue(isValid);
    }

    @Test
    public void LoginCLI_ValidLoginFormat_LoginCalled() throws InstantiationException, InvalidPropertiesFormatException {
        //Arrange
        String validInput = "developer@gmail.com password";
        ByteArrayInputStream in = new ByteArrayInputStream(validInput.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);
        Mockito.doReturn(true).when(cliManagerSpy).checkUserDetailFormat(validInput);

        //Act
        cliManager.loginCLI();

        //Assert
        verify(authenticationManager, times(1)).login(anyString(),anyString());
    }

    @Test
    public void LoginCLI_InvalidLoginFormat_LoginNotCalled() throws InstantiationException, InvalidPropertiesFormatException {
        //Arrange
        String invalidInput = "developer@gmail.compassword";
        ByteArrayInputStream in = new ByteArrayInputStream(invalidInput.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);
        Mockito.doReturn(false).when(cliManagerSpy).checkUserDetailFormat(invalidInput);

        //Act
        try {
            cliManager.loginCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(authenticationManager, never()).login(anyString(),anyString());
    }

    @Test
    public void ShowMenu_RegisterCommandAsAdministrator_RegisterCalled() throws InvalidPropertiesFormatException {
        //Arrange
        String registerCommand = "R";
        ByteArrayInputStream in = new ByteArrayInputStream(registerCommand.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);
        Mockito.doReturn(true).when(cliManagerSpy).isValidCommand(registerCommand);
        Mockito.doReturn(new Administrator("email@gmail.com", "p4ssword")).when(authenticationManager).getCurrentUser();

        //Act
        cliManager.showMenu();

        //Assert
        verify(cliManagerSpy, times(1)).registerCLI();
    }

    @Test
    public void ShowMenu_RegisterCommandAsDeveloper_RegisterNotCalled() throws InvalidPropertiesFormatException {
        //Arrange
        String registerCommand = "R";
        ByteArrayInputStream in = new ByteArrayInputStream(registerCommand.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);
        Mockito.doReturn(false).when(cliManagerSpy).isValidCommand(registerCommand);
        Mockito.doReturn(new Developer("email@gmail.com", "p4ssword")).when(authenticationManager).getCurrentUser();

        //Act
        cliManager.showMenu();

        //Assert
        verify(cliManagerSpy, never()).registerCLI();
    }
}
