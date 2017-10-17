package issuetracker;

import org.junit.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Ignore
public class ClusterManagerTest {
    @BeforeClass
    public static void beforeRun() {}

    @AfterClass
    public static void afterRun() {}

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    @Test
    public void GroupForumPosts_SingleQuestion_ClusterTitleCorrectlySet() { throw new NotImplementedException(); }

    @Test
    public void GroupForumPosts_MultipleQuestions_ClusterTitleCorrectlySet() { throw new NotImplementedException(); }

    @Test
    public void GroupForumPosts_SingleQuestion_OneForumPostCorrectlyGrouped() { throw new NotImplementedException(); }

    @Test
    public void GroupForumPosts_MultipleQuestions_RelatedForumPostsCorrectlyGrouped() { throw new NotImplementedException(); }

    @Test
    public void GroupForumPosts_SingleQuestion_OnePost() { throw new NotImplementedException(); }

    @Test
    public void GroupForumPosts_MultipleQuestions_CorrectNumberOfPosts() { throw new NotImplementedException(); }

    @Test
    public void GroupForumPosts_SingleQuestion_OneUserAffected() { throw new NotImplementedException(); }

    @Test
    public void GroupForumPosts_MultipleQuestions_CorrectNumberOfUsersAffected() { throw new NotImplementedException(); }

    @Test
    public void GroupForumPosts_NoQuestions_ThrowException() { throw new NotImplementedException(); }

    @Test
    public void SummariseForumPosts_ValidCluster_CorrectSummaryCreated() { throw new NotImplementedException(); }

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
