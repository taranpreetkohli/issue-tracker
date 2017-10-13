package issuetracker;

import org.junit.*;
import static org.junit.Assert.*;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.mockito.Mockito.mock;

public class ClusterManagerTest {
    Cluster singlePostCluster;
    Cluster multiplePostCluster;

    @BeforeClass
    public static void beforeRun() {
        singlePostCluster = mock(Cluster.class);
        singlePostCluster = mock(Cluster.class);
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
        cm.generateClusterTitle(singlePostCluster);

        //assert
        assertTrue(!singlePostCluster.getTitle().isEmpty());
    }

    @Test
    public void GenerateClusterTitle_MultiplePostCluster_ClusterTitleCorrectlySet() {
        ClusterManager cm = new ClusterManager();
        cm.generateClusterTitle(multiplePostCluster);

        assertTrue(!multiplePostCluster.getTitle().isEmpty());

    }

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
}
