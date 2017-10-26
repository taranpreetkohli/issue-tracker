package issuetracker.clustering;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.Developer;
import issuetracker.authentication.User;
import issuetracker.db.DBContext;
import issuetracker.exception.IssueNotFoundException;
import issuetracker.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.clusterers.DBSCAN;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.net.URL;
import java.util.*;

public class IssueManager {

    private static Logger logger = LoggerFactory.getLogger(IssueManager.class);
    private DBContext DBContext;

    public IssueManager(DBContext DBContext) {
        this.DBContext = DBContext;
    }

    public Issue generateCluster(String input) {
        try {
            URL testResourceURL = getClass().getClassLoader().getResource("test.arff");
            File testFile = new File(testResourceURL.getFile());
//            Instances instances = new Instances(new BufferedReader(new FileReader(testFile)));
            Instances instances = new Instances(new StringReader(input));
            StringToWordVector s = new StringToWordVector();
            s.setInputFormat(instances);
            instances = Filter.useFilter(instances, s);
            DBSCAN dbscan = new DBSCAN();
            System.out.println(instances.toString());
            dbscan.setEpsilon(1);
            dbscan.setMinPoints(1);
            dbscan.buildClusterer(instances);
            System.out.println(dbscan.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }


        Map<Integer, String> trainingData = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("training_data_content.txt").getFile()));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] values = currentLine.split("\\t");
                trainingData.put(Integer.parseInt(values[0]), String.join(" ", Arrays.copyOfRange(values, 1, values.length)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FastVector training = new FastVector();
        training.addElement(new Attribute("dunno", new FastVector()));

        Instances dataSet = new Instances("Training_Data", training, 1000);
        Instance inst = new Instance(1);
        Attribute a1 = dataSet.attribute("dunno");
        for (int i : trainingData.keySet()) {
            inst.setValue(a1, trainingData.get(i));
            dataSet.add(inst);
        }

        System.out.println(dataSet.toString());

        return null;
    }

    public List<Issue> retrieveIssuesOrderedByPriority() {
        List<Issue> issues = DBContext.retrieveAllIssues();
        if (issues != null) {
            issues.sort(Comparator.comparingInt(Issue::getPriority));
            Collections.reverse(issues);
        }
        return issues;
    }

    public List<Question> retrieveUnassignedQuestions() {
        return DBContext.retrieveUnassignedQuestions();
    }

    public void addQuestion(Issue issue, Question question) {
        issue.addQuestion(question);
        DBContext.updateIssue(issue);
        DBContext.assignQuestion(Long.toString(question.getQuestionID()));
    }

    public void removeQuestion(Issue issue, Question question) {
        List<Question> questionList = issue.getQuestions();

        for (Question q : questionList) {
            if (q.getQuestionID() == question.getQuestionID()) {
                questionList.remove(q);
                break;
            }
        }

        question.setAssignedToIssue(false);
        if (issue.getQuestions().size() == 0) {
            DBContext.deleteIssue(issue);
            removeIssueFromAssignedDevelopers(issue);
        } else {
            DBContext.updateIssue(issue);
            DBContext.unAssignQuestion(Long.toString(question.getQuestionID()));
        }
    }

    public void deleteIssue(Issue issue) {
        DBContext.deleteIssue(issue);
        removeIssueFromAssignedDevelopers(issue);
        if (issue.getQuestions() == null) {
            return;
        }

        List<Question> questionList = issue.getQuestions();
        for (Question question : questionList) {
            List<Question> posts = new ArrayList<>();
            posts.add(question);
            Issue newIssue = new Issue()
                    .setPosts(posts)
                    .setSummary(question.getInformation())
                    .setTitle(question.getQuestion());
            DBContext.saveNewIssue(newIssue);
        }
    }

    public void assignIssue(Administrator admin, Issue issue, Developer dev) {
        checkAdminAndDeveloperExist(admin, dev);
        if (DBContext.getIssue(issue.getId()) != null) {
            issue.addAssignee(dev);
            DBContext.updateIssue(issue);
            dev.addIssue(issue);
            DBContext.saveUser(dev);
        } else {
            throw new IssueNotFoundException();
        }
    }

    public void assignIssue(Issue issue, Developer dev) {
        Developer developer = (Developer) DBContext.getUser(dev.getEmail());
        if (dev == null) {
            throw new UserException("Developer not found!");
        }

        issue.addAssignee(developer);
        developer.addIssue(issue);
        DBContext.saveUser(developer);
        DBContext.updateIssue(issue);
    }

    public void unAssignIssue(Administrator admin, Issue issue, Developer dev) {
        checkAdminAndDeveloperExist(admin, dev);
        if (DBContext.getIssue(issue.getId()) != null) {
            issue.removeAssignee(dev);
            DBContext.updateIssue(issue);
            dev.removeIssue(issue);
            DBContext.saveUser(dev);
        } else {
            throw new IssueNotFoundException();
        }
    }

    public void unAssignIssue(Issue issue, Developer dev) {
        Developer developer = (Developer) DBContext.getUser(dev.getEmail());
        if (developer == null) {
            throw new UserException("Developer not found!");
        }

        issue.removeAssignee(dev);
        DBContext.updateIssue(issue);
        dev.removeIssue(issue);
        DBContext.saveUser(dev);
    }

    public void resolveIssue(Developer dev, Issue issue) {
        User developer = DBContext.getUser(dev.getEmail());
        if (developer == null || developer instanceof Administrator) {
            throw new UserException("Developer not found!");
        }

        if (DBContext.getIssue(issue.getId()) != null) {
            issue.resolve(dev);
            DBContext.updateIssue(issue);
            removeIssueFromAssignedDevelopers(issue);
        } else {
            throw new IssueNotFoundException();
        }

    }

    private void checkAdminAndDeveloperExist(Administrator admin, Developer dev) {
        User administrator = DBContext.getUser(admin.getEmail());
        if (administrator == null) {
            throw new UserException("Admin not found!");
        }

        User developer = DBContext.getUser(dev.getEmail());
        if (developer == null) {
            throw new UserException("Developer not found!");
        }
    }

    private void removeIssueFromAssignedDevelopers(Issue issue) {
        if (issue.getAssignees().size() > 0) {
            for (String email : issue.getAssignees()) {
                User developer = DBContext.getUser(email);
                if (developer != null) {
                    ((Developer) developer).removeIssue(issue);
                    DBContext.saveUser(developer);
                }
            }
        }
    }
}
