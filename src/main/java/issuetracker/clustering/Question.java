package issuetracker.clustering;

import issuetracker.exception.InvalidQuestionFormatException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import weka.core.converters.CSVLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Question implements Comparable<Question>{

    private long questionID;
    private String question;
    private String date;
    private String author;
    private long forumID;
    private String information;
    private String url;
    private boolean isAssignedToIssue;

    public static String toARFF(List<Question> questions) {
        if (questions == null || questions.size() == 0) {
            throw new IllegalArgumentException("Input question list must not be null or empty");
        }

        StringBuilder sb = Question.initARFF();
        for (Question q : questions) {
            sb.append(q.toARFF(false));
        }
        return sb.toString();
    }

    public Question() {
        // required empty constructor
    }

    public Question(String forumPost){
        List<String> propertyArray = new ArrayList<>();
        Reader in = new StringReader(forumPost);
        try {
            for(CSVRecord line: CSVFormat.DEFAULT.parse(in)){
                for (String item: line){
                    propertyArray.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setQuestionID(Long.parseLong(propertyArray.get(0)));
        setQuestion(propertyArray.get(1));
        setDate(propertyArray.get(2));
        setAuthor(propertyArray.get(3));
        setForumID(Long.parseLong(propertyArray.get(4)));
        setInformation(propertyArray.get(5));
        setUrl(propertyArray.get(6));
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

    private static StringBuilder initARFF() {
        StringBuilder sb = new StringBuilder();
        sb.append("@relation questions\n\n" +
                "@attribute questionID LONG\n" +
                "@attribute question STRING\n" +
                "@attribute date STRING\n" +
                "@attribute author STRING\n" +
                "@attribute forumID LONG\n" +
                "@attribute information STRING\n" +
                "@attribute url STRING\n\n" +
                "@data\n");
        return sb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(questionID)
                .append(",")
                .append(question)
                .append(",")
                .append(author)
                .append(",")
                .append(date)
                .append(",")
                .append(forumID)
                .append(",")
                .append(information)
                .append(",")
                .append(url);

        return sb.toString();
    }

    public String toARFF() {
        return this.toARFF(true);
    }

    public String toARFF(boolean whole) {
        if (getQuestion() == null || getDate() == null || getAuthor() == null
                || getInformation() == null || getUrl() == null) {
            throw new InvalidQuestionFormatException("One or more fields null: \n" + this.toString());
        }

        StringBuilder sb;
        if (whole) {
            sb = Question.initARFF();
        } else {
            sb = new StringBuilder();
        }
        sb.append(getQuestionID() + "," + getQuestion() + "," + getDate() + "," + getAuthor()
                + "," + getForumID() + "," + getInformation() + "," + getUrl() + "\n");
        return sb.toString();
    }

    @Override
    public int compareTo(Question o) {
        return (int) (this.getQuestionID() - o.getQuestionID());
    }

    public boolean isAssignedToIssue() {
        return isAssignedToIssue;
    }

    public void setAssignedToIssue(boolean assignedToIssue) {
        isAssignedToIssue = assignedToIssue;
    }
}
