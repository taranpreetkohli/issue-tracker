package issuetracker;

import issuetracker.clustering.Issue;
import issuetracker.clustering.IssueManager;
import issuetracker.clustering.Question;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

@Ignore
public class IssueManagerTest {

    private Question questionOne;
    private Question questionTwo;
    private Question questionThree;

    private IssueManager issueManager;

    @Before
    public void setup() {
        issueManager = new IssueManager();

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
    }

    @Test
    public void GenerateClusterTitle_SinglePostCluster_ClusterTitleCorrectlySet() {
        //arrange
        String input = buildInput(questionOne);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertFalse(issue.getTitle().isEmpty());
    }

    @Test
    public void GenerateClusterTitle_MultiplePostCluster_ClusterTitleCorrectlySet() {
        //arrange
        String input = buildInput(questionOne, questionTwo, questionThree);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertFalse(issue.getTitle().isEmpty());
    }

    @Test
    public void GroupForumPosts_SinglePost_OneForumPostCorrectlyGrouped() {
        //arrange
        String input = buildInput(questionOne);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertThat(issue.getQuestions(), hasSize(1));
    }

    @Test
    public void GroupForumPosts_MultiplePosts_RelatedForumPostsCorrectlyGrouped() {
        //arrange
        String input = buildInput(questionOne, questionTwo, questionThree);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertThat(issue.getQuestions(), hasSize(3));
    }

    @Test
    public void GroupForumPosts_SinglePost_OneUserAffected() {
        //arrange
        String input = buildInput(questionOne);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertEquals(1, issue.getUsers());
    }

    @Test
    public void GroupForumPosts_MultiplePosts_CorrectNumberOfUsersAffected() {
        //arrange
        String input = buildInput(questionOne, questionTwo, questionThree);

        //act
        Issue issue = issueManager.generateCluster(input);

        //assert
        assertEquals(3, issue.getUsers());
    }

    @Test(expected = IllegalArgumentException.class)
    public void GroupForumPosts_NullInput_ThrowException() {
        //arrange
        //act
        //should throw exception
        issueManager.generateCluster(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void GroupForumPosts_NoInput_ThrowException() {
        //arrange
        //act
        //should throw exception
        issueManager.generateCluster("");
    }

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
        throw new NotImplementedException();
    }

    @Test
    public void AddForumPost_ExistingCluster_ClusterHasNewForumPost() {
        // Arrange
        String input = buildInput(questionOne);

        // Act
        Issue issue = issueManager.generateCluster(input);
        issueManager.addQuestion(issue, questionTwo);

        // Assert
        assertThat(issue.getQuestions(), hasSize(2));
    }

    @Test
    public void AddForumPost_ClusterDoesNotExist_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException();
    }

    @Test
    public void RemoveForumPost_ExistingCluster_ClusterNoLongerContainsForumPost() {
        // Arrange
        String input = buildInput(questionOne, questionTwo, questionThree);

        // Act
        Issue issue = issueManager.generateCluster(input);
        issueManager.removeQuestion(issue, questionTwo);

        // Assert
        assertThat(issue.getQuestions(), hasSize(2));
    }

    @Test
    public void RemoveForumPost_SinglePostCluster_ClusterGetsDeleted() {
        // Arrange
        String input = buildInput(questionOne);

        // Act
        Issue issue = issueManager.generateCluster(input);
        issueManager.removeQuestion(issue, questionOne);

        // Assert
        // TODO
    }

    @Test
    public void RemoveForumPost_ClusterDoesNotExist_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException();
    }

    @Test
    public void DeleteCluster_ExistingCluster_ForumPostsPutIntoIndividualClusters() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException();
    }

    @Test
    public void DeleteCluster_ExistingCluster_RemovesClusterFromDatabase() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException();
    }

    @Test
    public void DeleteCluster_ClusterDoesNotExist_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException();
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
        // Act
        // Assert
        throw new org.apache.commons.lang3.NotImplementedException("Stub!");
    }

    @Test
    public void AdminAssignIssue_DeveloperDoesNotExist_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new org.apache.commons.lang3.NotImplementedException("Stub!");
    }

    @Test
    public void AdminUnassignIssue_DeveloperAssignedToThatIssue_UpdatesIssue() {
        // Arrange
        // Act
        // Assert
        throw new org.apache.commons.lang3.NotImplementedException("Stub!");
    }

    @Test
    public void AdminUnassignIssue_DeveloperNotAssignedToThatIssue_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new org.apache.commons.lang3.NotImplementedException("Stub!");
    }

    @Test
    public void DevResolveIssue_DeveloperAssignedToThatIssue_IssueResolved() {
        // Arrange
        // Act
        // Assert
        throw new org.apache.commons.lang3.NotImplementedException("Stub!");
    }

    @Test
    public void DevResolveIssue_DeveloperNotAssignedToThatIssue_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new org.apache.commons.lang3.NotImplementedException("Stub!");
    }

    @Test
    public void DevResolveIssue_IssueDoesNotExist_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new org.apache.commons.lang3.NotImplementedException("Stub!");
    }

}
