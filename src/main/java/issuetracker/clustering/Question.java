package issuetracker.clustering;

import java.util.List;

public class Question {

    private long questionID;
    private String question;
    private String date;
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

    public Question setQuestionID(long questionID) {
        this.questionID = questionID;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public Question setQuestion(String question) {
        this.question = question;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Question setDate(String date) {
        this.date = date;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Question setAuthor(String author) {
        this.author = author;
        return this;
    }

    public long getForumID() {
        return forumID;
    }

    public Question setForumID(long forumID) {
        this.forumID = forumID;
        return this;
    }

    public String getInformation() {
        return information;
    }

    public Question setInformation(String information) {
        this.information = information;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Question setUrl(String url) {
        this.url = url;
        return this;
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
