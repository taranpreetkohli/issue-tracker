package clustering;

import issuetracker.clustering.Question;
import issuetracker.exception.InvalidQuestionFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class QuestionTests {

    private static String singleQuestionExpectedARFF;
    private static String doubleQuestionExpectedARFF;
    private static Question toBeParsed;

    private Question questionOne;
    private Question questionTwo;

    @BeforeClass
    public static void beforeClass() {
        singleQuestionExpectedARFF = "@relation questions\n\n" +
                "@attribute questionID LONG\n" +
                "@attribute question STRING\n" +
                "@attribute date STRING\n" +
                "@attribute author STRING\n" +
                "@attribute forumID LONG\n" +
                "@attribute information STRING\n" +
                "@attribute url STRING\n\n" +
                "@data\n" +
                "44330,\"xero-php API, how to structure query\",1/06/2017,Rajiv Pardiwala,2286,\"I'm try to request " +
                "bank transactions from a specific account from a certain date using the xero-php API.$xero = new " +
                "PrivateApplication($config);print_r(  $xero->load('Accounting\\BankTransaction')->" +
                "setParameter('AccountID','XXXXXXX-4097-4028-9b99-XXXXXXXXX')->fromDate(new DateTime('2016-06-01" +
                " 00:00:00'))->execute());which becomes this:\"\"https://api.xero.com/api.xro/2.0/BankTransactions?" +
                "AccountID=XXXXXXX-4097-4028-9b99-XXXXXXXXX&fromDate=2016-06-01\"\"however, it is not filtering by" +
                " AccountID and instead returns transactions for both of my accounts.  The fromDate is ignored as well." +
                "  What is the correct url to do what I want?\",https://community.xero.com/developer/discussion/50795838/" + "\n";

        doubleQuestionExpectedARFF = singleQuestionExpectedARFF +
                "44331,Delivery Address on Invoice,31/05/2017,Sam Jones,2286,Hi,I am wondering if it is possible to add" +
                " a Delivery Address to an invoice. We have suppliers with multiple, constantly in use, delivery address." +
                " ,https://community.xero.com/developer/discussion/50739864/\n";

    }

    @Before
    public void setUp() throws Exception {
        questionOne = new Question()
                .setQuestionID(44330)
                .setQuestion("\"xero-php API, how to structure query\"")
                .setDate("1/06/2017")
                .setAuthor("Rajiv Pardiwala")
                .setForumID(2286)
                .setInformation("\"I'm try to request " +
                        "bank transactions from a specific account from a certain date using the xero-php API.$xero = new " +
                        "PrivateApplication($config);print_r(  $xero->load('Accounting\\BankTransaction')->" +
                        "setParameter('AccountID','XXXXXXX-4097-4028-9b99-XXXXXXXXX')->fromDate(new DateTime('2016-06-01" +
                        " 00:00:00'))->execute());which becomes this:\"\"https://api.xero.com/api.xro/2.0/BankTransactions?" +
                        "AccountID=XXXXXXX-4097-4028-9b99-XXXXXXXXX&fromDate=2016-06-01\"\"however, it is not filtering by" +
                        " AccountID and instead returns transactions for both of my accounts.  The fromDate is ignored as well." +
                        "  What is the correct url to do what I want?\"")
                .setUrl("https://community.xero.com/developer/discussion/50795838/");

        questionTwo = new Question()
                .setQuestionID(44331)
                .setQuestion("Delivery Address on Invoice")
                .setAuthor("Sam Jones")
                .setDate("31/05/2017")
                .setForumID(2286)
                .setInformation("Hi,I am wondering if it is possible to add a Delivery Address to an invoice. We have suppliers with multiple, constantly in use, delivery address. ")
                .setUrl("https://community.xero.com/developer/discussion/50739864/");
    }

    @Test
    public void toARFF_SingleQuestion_MakesARFFString() {
        // Arrange
        String actual;
        // Act
        actual = questionOne.toARFF();
        // Assert
        Assert.assertEquals(singleQuestionExpectedARFF, actual);
    }

    @Test
    public void toARFF_MultipleQuestions_MakesARFFString() {
        // Arrange
        String actual = "";
        List<Question> questionList = new ArrayList<>();
        questionList.add(questionOne);
        questionList.add(questionTwo);

        actual = Question.toARFF(questionList);
        // Assert
        Assert.assertEquals(doubleQuestionExpectedARFF, actual);
    }

    @Test(expected = InvalidQuestionFormatException.class)
    public void toARFF_MalformedQuestion_ThrowsFormatException() {
        // Arrange
        String actual = "";
        Question questionSpy = spy(questionOne);
        doReturn(null).when(questionSpy).getAuthor();

        // Act
        actual = questionSpy.toARFF();
    }

    @Test(expected = IllegalArgumentException.class)
    public void toARFF_QuestionListNull_ThrowsException() {
        Question.toARFF(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toARFF_QuestionListEmpty_ThrowsException() {
        Question.toARFF(new ArrayList<>());
    }
}
