package issuetracker;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.authentication.Developer;
import issuetracker.cli.CLIManager;
import issuetracker.cli.view.Command;
import issuetracker.clustering.IssueManager;
import issuetracker.exception.NoInputException;
import org.junit.*;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CLIManagerTest {
    private CLIManager cliManager;
    private AuthenticationManager authenticationManager;
    private IssueManager issueManager;
    @BeforeClass
    public static void beforeRun() {
    }

    @AfterClass
    public static void afterRun() {
    }

    @Before
    public void setUp() {
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        issueManager = Mockito.mock(IssueManager.class);
        cliManager = new CLIManager(authenticationManager, issueManager);
    }

    @After
    public void tearDown(){
        authenticationManager = Mockito.mock(AuthenticationManager.class);
    }

    @Test
    public void CheckTwoInputFormat_TwoInputs_ReturnsTrue() {
        //Arrange
        String validInput = "developer@gmail.com password";
        int num = 2;

        //Act
        boolean isValid = cliManager.checkNumInputFormat(validInput, num);

        //Assert
        assertTrue(isValid) ;
    }

    @Test
    public void CheckTwoInputFormat_FiveInputs_ReturnsFalse() {
        //Arrange
        String invalidInput = "developer@gmail.com password fdgf fhdfh vndhdf";
        int num = 2;

        //Act
        boolean isValid = cliManager.checkNumInputFormat(invalidInput, num);

        //Assert
        assertFalse(isValid) ;
    }

    @Test(expected = NoInputException.class)
    public void CheckTwoInputFormat_NoInput_NoInputExceptionThrown() {
        //Arrange
        String noInput = "";
        int num = 2;

        //Act Assert
        boolean isValid = cliManager.checkNumInputFormat(noInput, num);
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

        Command mockedCommand = Mockito.mock(Command.class);

        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("L", mockedCommand);
        cliManager.setViewMap(viewMap);

        //Act
        try {
            cliManager.logoutCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, times(1)).run(anyObject(), anyObject());
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

        Command mockedCommand = Mockito.mock(Command.class);

        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("L", mockedCommand);
        cliManager.setViewMap(viewMap);

        //Act
        try {
            cliManager.logoutCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, never()).run(anyObject(), anyObject());
    }

    @Test
    public void IsValidCommand_ValidCommand_ReturnsTrue() {
        //Arrange
        String validCommand = "L";
        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("L", Mockito.mock(Command.class));
        cliManager.setViewMap(viewMap);
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
        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("L", Mockito.mock(Command.class));
        cliManager.setViewMap(viewMap);
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
        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("V", Mockito.mock(Command.class));
        viewMap.put("M", Mockito.mock(Command.class));
        viewMap.put("L", Mockito.mock(Command.class));
        cliManager.setViewMap(viewMap);
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
        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("L", Mockito.mock(Command.class));
        cliManager.setViewMap(viewMap);
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
        Mockito.doReturn(true).when(cliManagerSpy).checkNumInputFormat(validInput, 2);

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
        Mockito.doReturn(false).when(cliManagerSpy).checkNumInputFormat(invalidInput, 2);

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

        Mockito.doReturn(true).when(cliManagerSpy).checkNumInputFormat(anyString(), anyInt());

        Administrator mockedAdmin = Mockito.mock(Administrator.class);
        Command mockedCommand = Mockito.mock(Command.class);

        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("R", mockedCommand);
        cliManager.setViewMap(viewMap);

        Mockito.doReturn(mockedAdmin).when(authenticationManager).getCurrentUser();

        //Act
        try {
            cliManager.registerCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, times(1)).run(anyObject(), anyObject());
    }

    @Test
    public void registerCLI_RegisterCommandAsAdministratorAndIncorrectFormat_RunNotCalled() throws InvalidPropertiesFormatException {
        //Arrange
        String registerCommand = "email@gmail.com p4ssword dfhdfhjdgj";
        ByteArrayInputStream in = new ByteArrayInputStream(registerCommand.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);

        Mockito.doReturn(false).when(cliManagerSpy).checkNumInputFormat(anyString(), anyInt());

        Administrator mockedAdmin = Mockito.mock(Administrator.class);
        Command mockedCommand = Mockito.mock(Command.class);

        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("R", mockedCommand);
        cliManager.setViewMap(viewMap);

        Mockito.doReturn(mockedAdmin).when(authenticationManager).getCurrentUser();

        //Act
        try {
            cliManager.registerCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, never()).run(anyObject(), anyObject());
    }

    @Test
    public void registerCLI_RegisterCommandAsDeveloper_RunNotCalled() throws InvalidPropertiesFormatException {
        //Arrange
        String registerCommand = "email@gmail.com p4ssword fdhgj";
        ByteArrayInputStream in = new ByteArrayInputStream(registerCommand.getBytes());
        System.setIn(in);
        CLIManager cliManagerSpy = Mockito.spy(cliManager);

        Mockito.doReturn(false).when(cliManagerSpy).checkNumInputFormat(anyString(), anyInt());

        Administrator mockedAdmin = Mockito.mock(Administrator.class);
        Command mockedCommand = Mockito.mock(Command.class);

        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("R", mockedCommand);
        cliManager.setViewMap(viewMap);

        Mockito.doReturn(mockedAdmin).when(authenticationManager).getCurrentUser();

        //Act
        try {
            cliManager.registerCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, never()).run(anyObject(), anyObject());
    }

    @Test
    public void ViewIssuesCLI_ViewCommandAsUser_RunCalled(){
        //Arrange
        String validInput = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(validInput.getBytes());
        System.setIn(in);

        Command mockedCommand = Mockito.mock(Command.class);
        Mockito.doReturn(Mockito.mock(Developer.class)).when(authenticationManager).getCurrentUser();

        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("V", mockedCommand);
        cliManager.setViewMap(viewMap);

        //Act
        try {
            cliManager.viewIssuesCLI();
        } catch (NoSuchElementException e) {}


        //Assert
        verify(mockedCommand, times(1)).run(anyObject(), anyObject());
    }

    @Test
    public void ManageIssuesCLI_ManageCommandAsUser_RunCalled(){
        //Arrange
        String validInput = "command id";
        ByteArrayInputStream in = new ByteArrayInputStream(validInput.getBytes());
        System.setIn(in);

        Command mockedCommand = Mockito.mock(Command.class);
        Mockito.doReturn(Mockito.mock(Developer.class)).when(authenticationManager).getCurrentUser();

        Map<String, Command> viewMap= new LinkedHashMap<>();
        viewMap.put("M", mockedCommand);
        cliManager.setViewMap(viewMap);

        //Act
        try {
            cliManager.manageIssuesCLI();
        } catch (NoSuchElementException e) {}

        //Assert
        verify(mockedCommand, times(1)).run(anyObject(), anyObject());

    }

}
