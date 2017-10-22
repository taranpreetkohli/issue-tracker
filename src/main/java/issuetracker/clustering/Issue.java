package issuetracker.clustering;

import issuetracker.authentication.Developer;
import issuetracker.exception.DeveloperNotAssignedException;
import issuetracker.exception.IssueAlreadyResolvedException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class Issue {

    private String id;
    private String title;
    private List<Question> posts;
    private List<String> users;
    private String summary;
    private List<String> assignees;
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

    public Issue addAssignee(Developer developer) {
        assignees.add(developer.getEmail());
        if (status == IssueStatus.UNASSIGNED) {
            status = IssueStatus.IN_PROGRESS;
        }
        return this;
    }

    public Issue removeAssignee(Developer developer) {
        if (assignees.contains(developer.getEmail())) {
            assignees.remove(developer.getEmail());
        } else {
            throw new DeveloperNotAssignedException("Cannot unassign developer from this issue!");
        }
        return this;
    }

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

    public enum IssueStatus {
        UNASSIGNED,
        RESOLVED,
        IN_PROGRESS
    }
}
