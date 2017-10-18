package issuetracker;

import issuetracker.clustering.Cluster;
import issuetracker.clustering.ClusterManager;
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
public class ClusterManagerTest {

    private Question questionOne;
    private Question questionTwo;
    private Question questionThree;

    private ClusterManager clusterManager;
    
    @Before
    public void setup() {
        clusterManager = new ClusterManager();

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
        List<Question> singleQuestion = new ArrayList<>();
        singleQuestion.add(questionOne);

        String input = Question.toARFF(singleQuestion);

        //act
        Cluster cluster = clusterManager.generateCluster(input);

        //assert
        assertFalse(cluster.getTitle().isEmpty());
    }

    @Test
    public void GenerateClusterTitle_MultiplePostCluster_ClusterTitleCorrectlySet() {
        //arrange
        List<Question> questions = new ArrayList<>();
        questions.add(questionOne);
        questions.add(questionTwo);
        questions.add(questionThree);

        String input = Question.toARFF(questions);

        //act
        Cluster cluster = clusterManager.generateCluster(input);

        //assert
        assertFalse(cluster.getTitle().isEmpty());
    }

    @Test
    public void GroupForumPosts_SinglePost_OneForumPostCorrectlyGrouped() {
        //arrange
        List<Question> singleQuestion = new ArrayList<>();
        singleQuestion.add(questionOne);

        String input = Question.toARFF(singleQuestion);

        //act
        Cluster cluster = clusterManager.generateCluster(input);

        //assert
        assertThat(cluster.getPosts(), hasSize(1));
    }

    @Test
    public void GroupForumPosts_MultiplePosts_RelatedForumPostsCorrectlyGrouped() {
        //arrange
        List<Question> questions = new ArrayList<>();
        questions.add(questionOne);
        questions.add(questionTwo);
        questions.add(questionThree);

        String input = Question.toARFF(questions);

        //act
        Cluster cluster = clusterManager.generateCluster(input);

        //assert
        assertThat(cluster.getPosts(), hasSize(3));
    }

    @Test
    public void GroupForumPosts_SinglePost_OneUserAffected() {
        //arrange
        List<Question> singleQuestion = new ArrayList<>();
        singleQuestion.add(questionOne);

        String input = Question.toARFF(singleQuestion);

        //act
        Cluster cluster = clusterManager.generateCluster(input);

        //assert
        assertEquals(1, cluster.getUsers());
    }

    @Test
    public void GroupForumPosts_MultiplePosts_CorrectNumberOfUsersAffected() {
        //arrange
        List<Question> questions = new ArrayList<>();
        questions.add(questionOne);
        questions.add(questionTwo);
        questions.add(questionThree);

        String input = Question.toARFF(questions);

        //act
        Cluster cluster = clusterManager.generateCluster(input);

        //assert
        assertEquals(3, cluster.getUsers());
    }

    @Test(expected = IllegalArgumentException.class)
    public void GroupForumPosts_NullInput_ThrowException() {
        //arrange
        //act
        //should throw exception
        clusterManager.generateCluster(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void GroupForumPosts_NoInput_ThrowException() {
        //arrange
        //act
        //should throw exception
        clusterManager.generateCluster("");
    }

    @Test
    public void SummariseForumPosts_ValidCluster_CorrectSummaryCreated() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException();
    }

    @Test
    public void SortIssues_IssuesList_IssuesOrderedCorrectlyByPriority() { throw new NotImplementedException(); }

    @Test
    public void AddForumPost_ExistingCluster_ClusterHasNewForumPost() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException();
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
        // Act
        // Assert
        throw new NotImplementedException();
    }

    @Test
    public void RemoveForumPost_SinglePostCluster_ClusterGetsDeleted() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException();
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

}
