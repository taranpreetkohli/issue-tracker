package issuetracker;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.cli.CLIManager;
import issuetracker.cli.view.Command;
import issuetracker.clustering.ClusterManager;
import issuetracker.exception.NoInputException;
import org.junit.*;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
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
    public void CheckConfirmationFormat_ValidInputYes_ReturnTrue() {
        //Arrange
        String validInputUpper = "Y";
        String validInputLower = "y";
        //Act
        boolean isValidUpper = cliManager.checkUserConfirmationFormat(validInputUpper);
        boolean isValidLower = cliManager.checkUserConfirmationFormat(validInputLower);
        //Assert
        assertTrue(isValidUpper);
        assertTrue(isValidLower);
    }

    @Test
    public void CheckConfirmationFormat_ValidInputNo_ReturnTrue() {
        //Arrange
        String validInputUpper = "N";
        String validInputLower = "n";
        //Act
        boolean isValidUpper = cliManager.checkUserConfirmationFormat(validInputUpper);
        boolean isValidLower = cliManager.checkUserConfirmationFormat(validInputLower);
        //Assert
        assertTrue(isValidUpper);
        assertTrue(isValidLower);
    }

    @Test(expected = NoInputException.class)
    public void CheckConfirmationFormat_NoInput_ReturnFalse() {
        //Arrange
        String emptyInput = "";
        //Act Assert
        boolean isValid = cliManager.checkUserConfirmationFormat(emptyInput);
    }

    @Test
    public void CheckConfirmationFormat_TooManyInputs_ReturnFalse() {
        //Arrange
        String invalidInput = "Y N";
        //Act
        boolean isValid = cliManager.checkUserConfirmationFormat(invalidInput);
        //Assert
        assertFalse(isValid);
    }

    @Test
    public void CheckConfirmationFormat_InvalidCharacter_ReturnFalse() {
        //Arrange
        String invalidInput = "W";
        //Act
        boolean isValid = cliManager.checkUserConfirmationFormat(invalidInput);
        //Assert
        assertFalse(isValid);
    }

    @Test
    public void CheckConfirmation_ConfirmationYes_ReturnTrue() {
        //Arrange
        String validInputUpper = "Y";
        String validInputLower = "y";
        //Act
        boolean isValidUpper = cliManager.checkUserConfirmation(validInputUpper);
        boolean isValidLower = cliManager.checkUserConfirmation(validInputLower);
        //Assert
        assertTrue(isValidUpper);
        assertTrue(isValidLower);
    }

    @Test
    public void CheckConfirmation_ConfirmationNo_ReturnFalse() {
        //Arrange
        String validInputUpper = "N";
        String validInputLower = "n";
        //Act
        boolean isValidUpper = cliManager.checkUserConfirmation(validInputUpper);
        boolean isValidLower = cliManager.checkUserConfirmation(validInputLower);
        //Assert
        assertFalse(isValidUpper);
        assertFalse(isValidLower);
    }

    @Test
    public void logoutCLI_LogoutConfirmed_RunCalled() throws InvalidPropertiesFormatException {
        //Arrange
        String logoutCommand = "Y";
        ByteArrayInputStream in = new ByteArrayInputStream(logoutCommand.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);

        Mockito.doReturn(true).when(cliManagerSpy).checkUserConfirmationFormat(anyString());
        Mockito.doReturn(true).when(cliManagerSpy).checkUserConfirmation(anyString());

        Administrator mockedAdmin = Mockito.mock(Administrator.class);
        HashMap<String, Command> mockedHm = Mockito.mock(LinkedHashMap.class);
        Command mockedCommand = Mockito.mock(Command.class);

        Mockito.doReturn(mockedAdmin).when(authenticationManager).getCurrentUser();
        Mockito.doReturn(mockedHm).when(mockedAdmin).getViewMap();
        Mockito.doReturn(mockedCommand).when(mockedHm).get(anyObject());

        //Act
        try {
            cliManager.logoutCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, times(1)).run(anyObject(), anyObject(), anyObject());
    }

    @Test
    public void logoutCLI_LogoutNotConfirmed_RunNotCalled() throws InvalidPropertiesFormatException {
        //Arrange
        String logoutCommand = "N";
        ByteArrayInputStream in = new ByteArrayInputStream(logoutCommand.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);

        Mockito.doReturn(true).when(cliManagerSpy).checkUserConfirmationFormat(anyString());
        Mockito.doReturn(false).when(cliManagerSpy).checkUserConfirmation(anyString());

        Administrator mockedAdmin = Mockito.mock(Administrator.class);
        HashMap<String, Command> mockedHm = Mockito.mock(LinkedHashMap.class);
        Command mockedCommand = Mockito.mock(Command.class);

        Mockito.doReturn(mockedAdmin).when(authenticationManager).getCurrentUser();
        Mockito.doReturn(mockedHm).when(mockedAdmin).getViewMap();
        Mockito.doReturn(mockedCommand).when(mockedHm).get(anyObject());

        //Act
        try {
            cliManager.logoutCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, never()).run(anyObject(), anyObject(), anyObject());
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
        try {
            cliManager.loginCLI();
        } catch (NullPointerException e) {}

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
    public void registerCLI_RegisterCommandAsAdministratorAndCorrectFormat_RunCalled() throws InvalidPropertiesFormatException {
        //Arrange
        String registerCommand = "email@gmail.com p4ssword";
        ByteArrayInputStream in = new ByteArrayInputStream(registerCommand.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);

        Mockito.doReturn(true).when(cliManagerSpy).checkUserDetailFormat(anyString());

        Administrator mockedAdmin = Mockito.mock(Administrator.class);
        HashMap<String, Command> mockedHm = Mockito.mock(LinkedHashMap.class);
        Command mockedCommand = Mockito.mock(Command.class);

        Mockito.doReturn(mockedAdmin).when(authenticationManager).getCurrentUser();
        Mockito.doReturn(mockedHm).when(mockedAdmin).getViewMap();
        Mockito.doReturn(mockedCommand).when(mockedHm).get(anyObject());

        //Act
        try {
            cliManager.registerCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, times(1)).run(anyObject(), anyObject(), anyObject());
    }

    @Test
    public void registerCLI_RegisterCommandAsAdministratorAndIncorrectFormat_RunNotCalled() throws InvalidPropertiesFormatException {
        //Arrange
        String registerCommand = "email@gmail.comp4ssword";
        ByteArrayInputStream in = new ByteArrayInputStream(registerCommand.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);

        Mockito.doReturn(false).when(cliManagerSpy).checkUserDetailFormat(anyString());

        Administrator mockedAdmin = Mockito.mock(Administrator.class);
        HashMap<String, Command> mockedHm = Mockito.mock(LinkedHashMap.class);
        Command mockedCommand = Mockito.mock(Command.class);

        Mockito.doReturn(mockedAdmin).when(authenticationManager).getCurrentUser();
        Mockito.doReturn(mockedHm).when(mockedAdmin).getViewMap();
        Mockito.doReturn(mockedCommand).when(mockedHm).get(anyObject());

        //Act
        try {
            cliManager.registerCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, never()).run(anyObject(), anyObject(), anyObject());
    }

    @Test
    public void registerCLI_RegisterCommandAsDeveloper_RunNotCalled() throws InvalidPropertiesFormatException {
        //Arrange
        String registerCommand = "email@gmail.comp4ssword";
        ByteArrayInputStream in = new ByteArrayInputStream(registerCommand.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);

        Mockito.doReturn(false).when(cliManagerSpy).checkUserDetailFormat(anyString());

        Administrator mockedAdmin = Mockito.mock(Administrator.class);
        HashMap<String, Command> mockedHm = Mockito.mock(LinkedHashMap.class);
        Command mockedCommand = Mockito.mock(Command.class);

        Mockito.doReturn(mockedAdmin).when(authenticationManager).getCurrentUser();
        Mockito.doReturn(mockedHm).when(mockedAdmin).getViewMap();
        Mockito.doReturn(mockedCommand).when(mockedHm).get(anyObject());

        //Act
        try {
            cliManager.registerCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, never()).run(anyObject(), anyObject(), anyObject());
    }

}
