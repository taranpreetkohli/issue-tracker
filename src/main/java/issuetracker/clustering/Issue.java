package issuetracker.clustering;

import java.util.HashSet;
import java.util.Set;

public class Issue {

    private String title;
    private Set<Question> posts;
    private Set<String> users;
    private String summary;

    public Issue(){
        posts = new HashSet<>();
        users = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public Set<Question> getQuestions() {
        return posts;
    }

    public Set<String> getUsers() {
        return users;
    }

    public String getSummary() {
        return summary;
    }

    public void addQuestion(Question question) {
        posts.add(question);
    }

    public void removeQuestion(Question question) {
        posts.remove(question);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Question> getPosts() {
        return posts;
    }

    public void setPosts(Set<Question> posts) {
        this.posts = posts;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
