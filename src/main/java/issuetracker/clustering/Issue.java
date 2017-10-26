package issuetracker.clustering;

import issuetracker.authentication.Developer;
import issuetracker.exception.DeveloperNotAssignedException;
import issuetracker.exception.IssueAlreadyResolvedException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Issue model
 */
public class Issue {

    /**
     * Stores the UID of this Issue
     */
    private String id;

    /**
     * Stores the title of this Issue
     */
    private String title;

    /**
     * Stores the Questions associated with this Issue
     */
    private List<Question> posts;

    /**
     * Stores the Users associated with this Issue
     */
    private List<String> users;

    /**
     * Stores the summary of this Issue
     */
    private String summary;

    /**
     * Stores the list of developers assigned to this Issue
     */
    private List<String> assignees;

    /**
     * Stores the status of this Issue
     */
    private IssueStatus status;

    public Issue() {
        posts = new ArrayList<>();
        users = new ArrayList<>();
        assignees = new ArrayList<>();
        status = IssueStatus.UNASSIGNED;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return posts;
    }

    public List<String> getUsers() {
        return users;
    }

    public String getSummary() {
        return summary;
    }

    public Issue addQuestion(Question question) {
        posts.add(question);
        return this;
    }

    public Issue removeQuestion(Question question) {
        posts.remove(question);
        return this;
    }

    public Issue setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Question> getPosts() {
        return posts;
    }

    public Issue setPosts(List<Question> posts) {
        this.posts = posts;
        return this;

    }

    public Issue setUsers(List<String> users) {
        this.users = users;
        return this;
    }

    public Issue setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * Adds a developer to this Issue, so that they're assigned to this Issue
     *
     * @param developer
     * @return
     */
    public Issue addAssignee(Developer developer) {
        assignees.add(developer.getEmail());
        if (status == IssueStatus.UNASSIGNED) {
            status = IssueStatus.IN_PROGRESS;
        }
        return this;
    }

    /**
     * Removes the Developer assigned to this issue
     * @param developer
     * @return
     */
    public Issue removeAssignee(Developer developer) {
        if (assignees.contains(developer.getEmail())) {
            assignees.remove(developer.getEmail());
        } else {
            throw new DeveloperNotAssignedException("Cannot unassign developer from this issue!");
        }
        return this;
    }

    /**
     * Resolves the issue
     * @param developer
     * @return
     */
    public Issue resolve(Developer developer) {
        if (status == IssueStatus.RESOLVED) {
            throw new IssueAlreadyResolvedException("This issue cannot be resolved again!");
        }

        if (assignees.contains(developer.getEmail())) {
            status = IssueStatus.RESOLVED;
        } else {
            throw new DeveloperNotAssignedException("Developer not assigned to this issue!");
        }
        return this;
    }

    public String getId() {
        return id;
    }

    public Issue setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        return new EqualsBuilder()
                .append(title, issue.title)
                .append(posts, issue.posts)
                .append(users, issue.users)
                .append(summary, issue.summary)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(posts)
                .append(users)
                .append(summary)
                .toHashCode();
    }

    public int getPriority() {
        return users.size();
    }

    public List<String> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<String> assignees) {
        this.assignees = assignees;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    /**
     * IssueStatus enumeration describing the current status of an Issue
     */
    public enum IssueStatus {
        UNASSIGNED,
        RESOLVED,
        IN_PROGRESS
    }
}
