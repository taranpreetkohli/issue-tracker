package issuetracker;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;

public class AdministratorTest {

    @Test
    public void AddForumPost_ExistingCluster_ClusterHasNewForumPost() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AddForumPost_ClusterDoesNotExist_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void RemoveForumPost_ExistingCluster_ClusterNoLongerContainsForumPost() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void RemoveForumPost_SinglePostCluster_ClusterGetsDeleted() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void RemoveForumPost_ClusterDoesNotExist_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void DeleteCluster_ExistingCluster_ForumPostsPutIntoIndividualClusters() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void DeleteCluster_ExistingCluster_RemovesClusterFromDatabase() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void DeleteCluster_ClusterDoesNotExist_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AssignIssue_ExistingDeveloper_UpdatesIssue() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AssignIssue_DeveloperDoesNotExist_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void UnassignIssue_DeveloperAssignedToThatIssue_UpdatesIssue() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void UnassignIssue_DeveloperNotAssignedToThatIssue_ExceptionThrown() {
        // Arrange
        // Act
        // Assert
        throw new NotImplementedException("Stub!");
    }

}
