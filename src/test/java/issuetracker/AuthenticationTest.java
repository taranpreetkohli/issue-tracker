package issuetracker;

import com.sun.java.util.jar.pack.Attribute;
import jdk.nashorn.internal.objects.NativeRegExpExecResult;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.junit.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AuthenticationTest {
    @BeforeClass
    public void beforeRun() {
    }

    @AfterClass
    public void afterRun() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void User_NewUserUniqueId_EqualsUnique() {
        throw new NotImplementedException();
    }

    @Test
    public void User_UserNameSetMakesName_EqualsString() {
        throw new NotImplementedException();
    }

    @Test
    public void User_AdministratorUsersAreAdmins_isTrue() {
        throw new NotImplementedException();
    }

    @Test
    public void User_UserLogin_LoginIsTrue() {
        throw new NotImplementedException();
    }

    @Test
    public void User_UserLogout_LoinIsFalse(){
        throw new NotImplementedException();
    }

    @Test
    public void User_SetUserEmailIsSetCorrectly_StringEquals(){
        throw new NotImplementedException();
    }

    @Test(expected = ValueException.class)
    public void User_MalformedEmailThrowsException_Exception(){
        throw new NotImplementedException();
    }

    @Test
    public void User_InvalidUsernameDoesNotAllowLogin_LoginIsFalse(){
        throw new NotImplementedException();
    }

    @Test
    public void User_InvalidPasswordDoesNotAllowLogin_LoginIsFalse(){
        throw new NotImplementedException();
    }
}
