package issuetracker;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;

public class AdministratorTest {

    @Test
    public void AddForumPost_ExistingCluster_ClusterHasNewForumPost() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AddForumPost_ClusterDoesNotExist_ExceptionThrown() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void RemoveForumPost_ExistingCluster_ClusterNoLongerContainsForumPost() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void RemoveForumPost_ClusterDoesNotExist_ExceptionThrown() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void DeleteCluster_ExistingCluster_ForumPostsPutIntoIndividualClusters() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void DeleteCluster_ExistingCluster_RemovesClusterFromDatabase() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void DeleteCluster_ClusterDoesNotExist_ExceptionThrown() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AssignIssue_ExistingDeveloper_UpdatesIssue() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void AssignIssue_DeveloperDoesNotExist_ExceptionThrown() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void UnassignIssue_DeveloperAssignedToThatIssue_UpdatesIssue() {
        throw new NotImplementedException("Stub!");
    }

    @Test
    public void UnassignIssue_DeveloperNotAssignedToThatIssue_ExceptionThrown() {
        throw new NotImplementedException("Stub!");
    }

}
