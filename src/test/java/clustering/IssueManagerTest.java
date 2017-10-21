package clustering;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.Developer;
import issuetracker.clustering.Issue;
import issuetracker.clustering.IssueManager;
import issuetracker.clustering.Question;
import issuetracker.db.FirebaseAdapter;
import issuetracker.exception.DeveloperNotAssignedException;
import issuetracker.exception.IssueAlreadyResolvedException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class IssueManagerTest {

    private Question questionOne;
    private Question questionTwo;
    private Question questionThree;

    private IssueManager issueManager;

    @Mock
    private FirebaseAdapter firebaseAdapter;

    @Mock
    private Administrator admin;

    @Mock
    private Developer developer;

    @Mock
    private Issue issue;

    @Before
    public void setup() {
        issueManager = new IssueManager(firebaseAdapter);

        questionOne = new Question()
                .setQuestionID(44330)
                .setQuestion("xero-php API, how to structure query")
                .setAuthor("Rajiv Pardiwala")
                .setDate("1/06/2017")
                .setForumID(2286)
                .setInformation("I'm try to request bank transactions from a specific account from a certain date using the xero-php API.")
                .setUrl("https://community.xero.com/developer/discussion/50795838/");

        questionTwo = new Question()
                .setQuestionID(44331)
                .setQuestion("Delivery Address on Invoice")
                .setAuthor("Sam Jones")
                .setDate("31/05/2017")
                .setForumID(2286)
                .setInformation("Hi,I am wondering if it is possible to add a Delivery Address to an invoice. We have suppliers with multiple, constantly in use, delivery address. ")
                .setUrl("https://community.xero.com/developer/discussion/50739864/");

        questionThree = new Question()
                .setQuestionID(44332)
                .setQuestion("Getting zero dollar value categories included in Profit and Loss Report")
                .setAuthor("stu barr")
                .setDate("9/05/2017")
                .setForumID(2286)
                .setInformation("I am having trouble when I GET a Profit and Loss report through the api. It gets all rows that have a dollar value greater then zero but does not include any rows with a zero dollar value.")
                .setUrl("https://community.xero.com/developer/discussion/49157379/");


        Mockito.doReturn("admin@gmail.com").when(admin).getEmail();
        Mockito.doReturn("dev@gmail.com").when(developer).getEmail();
        Mockito.doReturn(admin).when(firebaseAdapter).getUser("admin@gmail.com");
        Mockito.doReturn(developer).when(firebaseAdapter).getUser("dev@gmail.com");
        Mockito.doReturn(issue).when(firebaseAdapter).getIssue(Mockito.anyString());
    }

    @Ignore
    @Test
    public void GenerateIssueTitle_SinglePostIssue_IssueTitleCorrectlySet() {
        //arrange
        String input = buildInput(questionOne);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertFalse(issue.getTitle().isEmpty());
    }

    @Ignore
    @Test
    public void GenerateIssueTitle_MultiplePostIssue_IssueTitleCorrectlySet() {
        //arrange
        String input = buildInput(questionOne, questionTwo, questionThree);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertFalse(issue.getTitle().isEmpty());
    }

    @Ignore
    @Test
    public void GroupForumPosts_SinglePost_OneForumPostCorrectlyGrouped() {
        //arrange
        Issue expectedIssue = new Issue();
        expectedIssue.setId("fakeId")
                .addQuestion(questionOne);
        //act
        Issue issue = issueManager.generateCluster(questionOne.toARFF());

        //assert
        assertThat(issue.getQuestions(), hasSize(1));
        assertEquals(issue.getQuestions().toArray(new Question[1])[0].getQuestion(), questionOne.getQuestion());
    }

    @Ignore
    @Test
    public void GroupForumPosts_MultiplePosts_RelatedForumPostsCorrectlyGrouped() {
        //arrange
        Issue expectedIssue = new Issue().setId("fakeId")
                .addQuestion(questionOne)
                .addQuestion(questionTwo)
                .addQuestion(questionThree);
        List<Question> questions = new LinkedList<>();
        questions.add(questionOne);
        questions.add(questionTwo);
        questions.add(questionThree);

        //act
        Issue issue = issueManager.generateCluster(Question.toARFF(questions));

        //assert
        assertThat(issue.getQuestions(), hasSize(3));
    }

    @Ignore
    @Test
    public void GroupForumPosts_SinglePost_OneUserAffected() {
        //arrange
        String input = buildInput(questionOne);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertEquals(1, issue.getUsers().size());
    }

    @Ignore
    @Test
    public void GroupForumPosts_MultiplePosts_CorrectNumberOfUsersAffected() {
        //arrange
        String input = buildInput(questionOne, questionTwo, questionThree);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertEquals(3, issue.getUsers().size());
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void GroupForumPosts_NullInput_ThrowException() {
        //arrange
        //act
        //should throw exception
        issueManager.generateCluster(null);
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void GroupForumPosts_NoInput_ThrowException() {
        //arrange
        //act
        //should throw exception
        issueManager.generateCluster("");
    }

    @Ignore
    @Test
    public void GroupForumPosts_MultiplePosts_CorrectSummaryCreated() {
        //arrange
        String input = buildInput(questionOne, questionTwo, questionThree);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertFalse(issue.getSummary().isEmpty());
    }

    @Test
    public void SortIssues_IssuesList_IssuesOrderedCorrectlyByPriority() {
        //Arrange
        List<Issue> issues = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Issue mock = Mockito.mock(Issue.class);
            int upper = 100;
            int lower = 1;
            int randomNum = new Random().nextInt((upper - lower) + 1) + lower;
            Mockito.doReturn(randomNum).when(mock).getPriority();
            issues.add(mock);
        }

        Mockito.doReturn(issues).when(firebaseAdapter).retrieveAllIssues();
        // Act
        List<Issue> returnedIssues = issueManager.retrieveIssuesOrderedByPriority();

        // Assert
        Mockito.verify(firebaseAdapter, times(1)).retrieveAllIssues();
        assertNotNull(returnedIssues);
        for (int i = 1; i < returnedIssues.size(); i++) {
            assertTrue(returnedIssues.get(i - 1).getPriority() >= returnedIssues.get(i).getPriority());
        }
    }

    @Test
    public void AddForumPost_ExistingIssue_IssueHasNewForumPost() {
        // Arrange
        Question existingQuestion = Mockito.mock(Question.class);
        Set<Question> questions = new HashSet<>();
        questions.add(existingQuestion);
        Mockito.doReturn(questions).when(issue).getPosts();

        // Act
        issueManager.addQuestion(issue, questionOne);

        // Assert
        Mockito.verify(firebaseAdapter, times(1)).updateIssue(issue);
        Mockito.verify(issue, times(1)).addQuestion(questionOne);
    }

    @Test
    public void RemoveForumPost_ExistingIssue_IssueNoLongerContainsForumPost() {
        // Arrange
        Issue issue = new Issue();
        issue.addQuestion(questionOne);
        issue.addQuestion(questionTwo);
        issue.addQuestion(questionThree);

        // Act
        issueManager.removeQuestion(issue, questionTwo);

        // Assert
        Mockito.verify(firebaseAdapter, times(1)).updateIssue(issue);
        assertThat(issue.getQuestions(), hasSize(2));
    }

    @Test
    public void RemoveForumPost_SinglePostIssue_IssueGetsDeleted() {
        // Arrange
        Issue issue = new Issue();
        issue.addQuestion(questionOne);

        // Act
        issueManager.removeQuestion(issue, questionOne);

        // Assert
        Mockito.verify(firebaseAdapter, times(1)).deleteIssue(issue);
        assertThat(issue.getQuestions(), hasSize(0));
    }

    @Test
    public void DeleteIssue_ExistingIssue_RemovesIssueFromDatabase() {
        // Arrange
        Issue issue = Mockito.mock(Issue.class);
        // Act
        issueManager.deleteIssue(issue);
        // Assert
        Mockito.verify(firebaseAdapter, times(1)).deleteIssue(issue);
    }

    private String buildInput(Question... questions) {
        if (questions == null) {
            return null;
        }

        List<Question> questionList = new ArrayList<>();
        for (Question question : questions) {
            questionList.add(question);
        }

        return Question.toARFF(questionList);
    }

    @Test
    public void AdminAssignIssue_ExistingDeveloper_UpdatesIssue() {
        // Arrange
        Issue issue = Mockito.mock(Issue.class);

        // Act
        issueManager.assignIssue(admin, issue, developer);
        // Assert
        Mockito.verify(issue, times(1)).addAssignee(developer);
        Mockito.verify(firebaseAdapter, times(1)).updateIssue(issue);
        Mockito.verify(developer, times(1)).addIssue(issue);
        Mockito.verify(firebaseAdapter, times(1)).saveUser(developer);
    }

    @Test
    public void AdminUnassignIssue_DeveloperAssignedToThatIssue_UpdatesIssue() {
        // Arrange
        Issue issue = Mockito.mock(Issue.class);
        issueManager.assignIssue(admin, issue, developer);

        // Act
        issueManager.unAssignIssue(admin, issue, developer);
        // Assert
        Mockito.verify(issue, times(1)).removeAssignee(developer);
        Mockito.verify(firebaseAdapter, times(2)).updateIssue(issue);
        Mockito.verify(developer, times(1)).removeIssue(issue);
        Mockito.verify(firebaseAdapter, times(2)).saveUser(developer);
    }

    @Test(expected = DeveloperNotAssignedException.class)
    public void AdminUnassignIssue_DeveloperNotAssignedToThatIssue_ExceptionThrown() {
        // Arrange
        Issue issue = new Issue();

        // Act
        issueManager.unAssignIssue(admin, issue, developer);
        // should throw an exception
    }

    @Test
    public void DevResolveIssue_DeveloperAssignedToThatIssue_IssueResolved() {
        // Arrange
        Issue issue = Mockito.mock(Issue.class);
        issueManager.assignIssue(admin, issue, developer);

        Set<Developer> assignees = new HashSet<>();
        assignees.add(developer);

        Mockito.doReturn(assignees).when(issue).getAssignees();

        // Act
        issueManager.resolveIssue(developer, issue);
        // Assert
        Mockito.verify(issue, times(1)).resolve(developer);
        Mockito.verify(firebaseAdapter, times(2)).updateIssue(issue);
        Mockito.verify(developer, times(1)).removeIssue(issue);
        Mockito.verify(firebaseAdapter, times(2)).saveUser(developer);
    }

    @Test(expected = DeveloperNotAssignedException.class)
    public void DevResolveIssue_DeveloperNotAssignedToThatIssue_ExceptionThrown() {
        // Arrange
        Issue issue = new Issue();

        // Act
        issueManager.resolveIssue(developer, issue);
        // show throw exception
    }

    @Test(expected = IssueAlreadyResolvedException.class)
    public void DevResolveIssue_IssueAlreadyResolved_ExceptionThrown() {
        // Arrange
        Issue issue = new Issue();
        issueManager.assignIssue(admin, issue, developer);

        // Act
        issueManager.resolveIssue(developer, issue);
        issueManager.resolveIssue(developer, issue);
        // should throw exception
    }

}
