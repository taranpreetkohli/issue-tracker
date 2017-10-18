package issuetracker;

import org.junit.*;
import static org.junit.Assert.*;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Ignore
public class ClusterManagerTest {
    Cluster singlePostCluster;
    Cluster multiplePostCluster;

    @BeforeClass
    public static void beforeRun() {
        singlePostCluster = new Cluster();
        multiplePostCluster = new Cluster();
    }

    @AfterClass
    public static void afterRun() {}

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    @Test
    public void GenerateClusterTitle_SinglePostCluster_ClusterTitleCorrectlySet() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        cm.generateClusterTitle(singlePostCluster);

        //assert
        assertTrue(!singlePostCluster.getTitle().isEmpty());
    }

    @Test
    public void GenerateClusterTitle_MultiplePostCluster_ClusterTitleCorrectlySet() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        cm.generateClusterTitle(multiplePostCluster);

        //assert
        assertTrue(!multiplePostCluster.getTitle().isEmpty());

    }

    @Test
    public void GroupForumPosts_SinglePost_OneForumPostCorrectlyGrouped() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        Cluster newSinglePostCluster = cm.groupForumPosts(singlePostData);

        //assert
        assertThat(newSinglePostCluster.getPosts(), hasSize(1));
    }

    @Test
    public void GroupForumPosts_MultiplePosts_RelatedForumPostsCorrectlyGrouped() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        Cluster newMultiplePostCluster = cm.groupForumPosts(multiplePostData);

        //assert
        assertThat(newMultiplePostCluster.getPosts(), hasSize(3));
    }

    @Test
    public void GroupForumPosts_SinglePost_OnePost() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        Cluster newSinglePostCluster = cm.groupForumPosts(singlePostData);

        //assert
        assertEquals(1, newSinglePostCluster.getNumPosts());
    }

    @Test
    public void GroupForumPosts_MultiplePosts_CorrectNumberOfPosts() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        Cluster newMultiplePostCluster = cm.groupForumPosts(multiplePostData);

        //assert
        assertEquals(3, newMultiplePostCluster.getNumPosts());
    }

    @Test
    public void GroupForumPosts_SinglePost_OneUserAffected() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        Cluster newSinglePostCluster = cm.groupForumPosts(singlePostData);

        //assert
        assertEquals(1, newSinglePostCluster.getNumUsers());
    }

    @Test
    public void GroupForumPosts_MultiplePosts_CorrectNumberOfUsersAffected() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        Cluster newSinglePostCluster = cm.groupForumPosts(singlePostData);

        //assert
        //guessing number for now
        assertEquals(2, newSinglePostCluster.getNumUsers());
    }

    @Test
    public void GroupForumPosts_NoPosts_ThrowException() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        //should throw exception
        Cluster newSinglePostCluster = cm.groupForumPosts(noPostData);
    }

    @Test
    public void SummariseForumPosts_ValidCluster_CorrectSummaryCreated() {
        //arrange
        ClusterManager cm = new ClusterManager();

        //act
        cm.summarisePosts(multiplePostCluster);

        //assert
        assertThat(multiplePostCluster.getSummary(), not(isEmptyOrNullString()));
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
