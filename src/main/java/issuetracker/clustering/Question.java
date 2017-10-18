package issuetracker.clustering;

import java.util.Date;
import java.util.List;

public class Question {

    private long questionID;
    private String question;
    private Date date;
    private String author;
    private long forumID;
    private String information;
    private String url;

    public static String toARFF(List<Question> questions) {
        StringBuilder sb = Question.initARFF();
        for (Question q : questions) {
            sb.append(q.toARFF(false));
        }
        return sb.toString();
    }

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
                "questionID=" + getQuestionID() +
                ", question='" + getQuestion() + '\'' +
                ", date=" + getDate() +
                ", author='" + getAuthor() + '\'' +
                ", forumID=" + getForumID() +
                ", information='" + getInformation() + '\'' +
                ", url='" + getUrl() + '\'' +
                '}';
    }

    private static StringBuilder initARFF() {
        StringBuilder sb = new StringBuilder();
        sb.append("@relation questions\n\n");
        sb.append("@attribute questionID LONG\\n\" +\n" +
                "                \"@attribute question STRING\\n\" +\n" +
                "                \"@attribute date STRING\\n\" +\n" +
                "                \"@attribute author STRING\\n\" +\n" +
                "                \"@attribute forumID LONG\\n\" +\n" +
                "                \"@attribute information INFORMATION\\n\" +\n" +
                "                \"@attribute url STRING\\n\\n\" +\n" +
                "                \"@data\\n\"");
        return sb;
    }

    public String toARFF() {
        return this.toARFF(true);
    }

    public String toARFF(Boolean whole) {
        StringBuilder sb;
        if (whole) {
            sb = Question.initARFF();
        } else {
            sb = new StringBuilder();
        }
        sb.append(getQuestionID() + "," + getQuestion() + "," + getDate() + "," + getAuthor() + "," + getForumID() + "," + getInformation() + "," + getUrl());
        return sb.toString();
    }
}
