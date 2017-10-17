package issuetracker.clustering;

import java.util.Date;

public class Question {

    private long questionID;
    private String question;
    private Date date;
    private String author;
    private long forumID;
    private String information;
    private String url;

    public Question() {
        // required empty constructor
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getForumID() {
        return forumID;
    }

    public void setForumID(long forumID) {
        this.forumID = forumID;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionID=" + questionID +
                ", question='" + question + '\'' +
                ", date=" + date +
                ", author='" + author + '\'' +
                ", forumID=" + forumID +
                ", information='" + information + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
