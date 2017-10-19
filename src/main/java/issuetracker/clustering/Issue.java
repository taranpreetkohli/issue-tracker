package issuetracker.clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
}
