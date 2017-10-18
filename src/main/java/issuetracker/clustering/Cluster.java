package issuetracker.clustering;

import java.util.Set;

public class Cluster {

    private String title;
    private Set<Question> posts;
    private Set<String> users;
    private String summary;

    public String getTitle() {
        return title;
    }

    public Set<Question> getPosts() {
        return posts;
    }

    public Set<String> getUsers() {
        return users;
    }

    public String getSummary() {
        return summary;
    }

    public void addQuestion(Question question) {
        // add question to set of posts
    }

    public void removeQuestion(Question question) {
        // remove question from set of posts
    }
}
