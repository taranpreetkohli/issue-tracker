package issuetracker;

import issuetracker.clustering.Cluster;
import issuetracker.clustering.ClusterManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

@Ignore
public class ClusterManagerTest {

    String singlePostInput;
    String multiplePostInput;

    ClusterManager clusterManager;
    
    @Before
    public void setup() {
        clusterManager = new ClusterManager();
    }

    @Test
    public void GenerateClusterTitle_SinglePostCluster_ClusterTitleCorrectlySet() {
        //arrange

        //act
        Cluster cluster = clusterManager.generateCluster(singlePostInput);

        //assert
        assertFalse(cluster.getTitle().isEmpty());
    }

    @Test
    public void GenerateClusterTitle_MultiplePostCluster_ClusterTitleCorrectlySet() {
        //arrange

        //act
        Cluster cluster = clusterManager.generateCluster(multiplePostInput);

        //assert
        assertFalse(cluster.getTitle().isEmpty());
    }

    @Test
    public void GroupForumPosts_SinglePost_OneForumPostCorrectlyGrouped() {
        //arrange

        //act
        Cluster cluster = clusterManager.generateCluster(singlePostInput);

        //assert
        assertThat(cluster.getPosts(), hasSize(1));
    }

    @Test
    public void GroupForumPosts_MultiplePosts_RelatedForumPostsCorrectlyGrouped() {
        //arrange

        //act
        Cluster cluster = clusterManager.generateCluster(multiplePostInput);

        //assert
        assertThat(cluster.getPosts(), hasSize(3));
    }

    @Test
    public void GroupForumPosts_SinglePost_OneUserAffected() {
        //arrange

        //act
        Cluster newSinglePostCluster = clusterManager.generateCluster(singlePostInput);

        //assert
        assertEquals(1, newSinglePostCluster.getUsers());
    }

    @Test
    public void GroupForumPosts_MultiplePosts_CorrectNumberOfUsersAffected() {
        //arrange

        //act
        Cluster newSinglePostCluster = clusterManager.generateCluster(multiplePostInput);

        //assert
        assertEquals(2, newSinglePostCluster.getUsers());
    }

    @Test(expected = IllegalArgumentException.class)
    public void GroupForumPosts_NullInput_ThrowException() {
        //arrange
        //act
        //should throw exception
        Cluster newSinglePostCluster = clusterManager.generateCluster(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void GroupForumPosts_NoInput_ThrowException() {
        //arrange
        //act
        //should throw exception
        Cluster newSinglePostCluster = clusterManager.generateCluster("");
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
