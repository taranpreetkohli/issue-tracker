package clustering;

import com.sun.java.util.jar.pack.Attribute;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.zip.DataFormatException;

@RunWith(MockitoJUnitRunner.class)
public class ARFFParserTests {

    private ARFFParser parser;

    private static String expectedARFF;
    private static String toBeParsed;

    @BeforeClass
    public static void beforeClass() {
        expectedARFF = "@relation TDD\n\n" +
                "@attribute questionID LONG\n" +
                "@attribute question STRING\n" +
                "@attribute date STRING\n" +
                "@attribute author STRING\n" +
                "@attribute forumID LONG\n" +
                "@attribute information INFORMATION\n" +
                "@attribute url STRING\n\n" +
                "@data\n" +
                "44330,\"xero-php API, how to structure query\",1/06/2017,Rajiv Pardiwala,2286,\"I'm try to request " +
                "bank transactions from a specific account from a certain date using the xero-php API.$xero = new " +
                "PrivateApplication($config);print_r(  $xero->load('Accounting\\BankTransaction')->" +
                "setParameter('AccountID','XXXXXXX-4097-4028-9b99-XXXXXXXXX')->fromDate(new DateTime('2016-06-01" +
                " 00:00:00'))->execute());which becomes this:\"\"https://api.xero.com/api.xro/2.0/BankTransactions?" +
                "AccountID=XXXXXXX-4097-4028-9b99-XXXXXXXXX&fromDate=2016-06-01\"\"however, it is not filtering by" +
                " AccountID and instead returns transactions for both of my accounts.  The fromDate is ignored as well." +
                "  What is the correct url to do what I want?\",https://community.xero.com/developer/discussion/50795838/";

        toBeParsed = "44330,\"xero-php API, how to structure query\",1/06/2017,Rajiv Pardiwala,2286,\"I'm try to request " +
                "bank transactions from a specific account from a certain date using the xero-php API.$xero = new " +
                "PrivateApplication($config);print_r(  $xero->load('Accounting\\BankTransaction')->" +
                "setParameter('AccountID','XXXXXXX-4097-4028-9b99-XXXXXXXXX')->fromDate(new DateTime('2016-06-01" +
                " 00:00:00'))->execute());which becomes this:\"\"https://api.xero.com/api.xro/2.0/BankTransactions?" +
                "AccountID=XXXXXXX-4097-4028-9b99-XXXXXXXXX&fromDate=2016-06-01\"\"however, it is not filtering by" +
                " AccountID and instead returns transactions for both of my accounts.  The fromDate is ignored as well." +
                "  What is the correct url to do what I want?\",https://community.xero.com/developer/discussion/50795838/";
    }

    @Before
    public void setUp() throws Exception {
        parser = new ARFFParser();
    }

    @Test
    public void toARFF_SingleQuestion_MakesARFFString() {
        // Arrange
        String actual = "";
        // Act
        actual = parser.parse(toBeParsed);
        // Assert
        Assert.assertEquals(expectedARFF, actual);
    }

    @Test
    public void toARFF_MultipleQuestions_MakesARFFString() {
        // Arrange
        String actual = "";
        String expected = "@relation TDD\n\n" +
                "@attribute questionID LONG\n" +
                "@attribute question STRING\n" +
                "@attribute date STRING\n" +
                "@attribute author STRING\n" +
                "@attribute forumID LONG\n" +
                "@attribute information INFORMATION\n" +
                "@attribute url STRING\n\n" +
                "@data\n" +
                "44330,\"xero-php API, how to structure query\",1/06/2017,Rajiv Pardiwala,2286,\"I'm try to request " +
                "bank transactions from a specific account from a certain date using the xero-php API.$xero = new " +
                "PrivateApplication($config);print_r(  $xero->load('Accounting\\BankTransaction')->" +
                "setParameter('AccountID','XXXXXXX-4097-4028-9b99-XXXXXXXXX')->fromDate(new DateTime('2016-06-01" +
                " 00:00:00'))->execute());which becomes this:\"\"https://api.xero.com/api.xro/2.0/BankTransactions?" +
                "AccountID=XXXXXXX-4097-4028-9b99-XXXXXXXXX&fromDate=2016-06-01\"\"however, it is not filtering by" +
                " AccountID and instead returns transactions for both of my accounts.  The fromDate is ignored as well." +
                "  What is the correct url to do what I want?\",https://community.xero.com/developer/discussion/50795838/\n" +
                "44331,Delivery Address on Invoice,31/05/2017,Sam Jones,2286,\"Hi,I am wondering if it is possible to add a Delivery Address to an invoice. We have suppliers with multiple, constantly in use, delivery address. We need to add this address onto the invoice in some way so the supplier knows what address it was for.I know in the docs it states \"\"It is not possible to manually override the address on a per-invoice basis.\"\" but does anyone know a way around this or an alternative solution to record the address on an invoice?ThanksSam\",https://community.xero.com/developer/discussion/50739864/";
        String parsed = toBeParsed + "\n" + "44331,Delivery Address on Invoice,31/05/2017,Sam Jones,2286,\"Hi,I am wondering if it is possible to add a Delivery Address to an invoice. We have suppliers with multiple, constantly in use, delivery address. We need to add this address onto the invoice in some way so the supplier knows what address it was for.I know in the docs it states \"\"It is not possible to manually override the address on a per-invoice basis.\"\" but does anyone know a way around this or an alternative solution to record the address on an invoice?ThanksSam\",https://community.xero.com/developer/discussion/50739864/";
        // Act
        actual = parser.parse(parsed);
        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = DataFormatException.class)
    public void toARFF_MalformedQuestion_ThrowsFormatException() {
        // Arrange
        String actual = "";
        String toParse = "This should not work, because it is not the right format";
        // Act
        actual = parser.parse(toParse);
        // Assert
        Assert.fail();
    }
}
