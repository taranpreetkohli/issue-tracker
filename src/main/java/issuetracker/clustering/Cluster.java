package issuetracker.clustering;

import java.util.Set;

public class Cluster {

    private String title;
    private Set<Question> posts;
    private Set<String> users;

    public String getTitle() {
        return title;
    }

    public Set<Question> getPosts() {
        return posts;
    }

    public Set<String> getUsers() {
        return users;
    }
}
