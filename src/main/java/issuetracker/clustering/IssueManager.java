package issuetracker.clustering;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.Developer;
import issuetracker.authentication.User;
import issuetracker.db.DBContext;
import issuetracker.exception.IssueNotFoundException;
import issuetracker.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IssueManager {

    private static Logger logger = LoggerFactory.getLogger(IssueManager.class);
    private DBContext dBContext;

    public IssueManager(DBContext dBContext) {
        this.dBContext = dBContext;
//        try {
//            URL testResourceURL = getClass().getClassLoader().getResource("test.arff");
//            File testFile = new File(testResourceURL.getFile());
//            Instances instances = new Instances(new BufferedReader(new FileReader(testFile)));
//            StringToWordVector s = new StringToWordVector();
//            s.setInputFormat(instances);
//            instances = Filter.useFilter(instances, s);
//            DBSCAN dbscan = new DBSCAN();
//            System.out.println(instances.toString());
//            dbscan.setEpsilon(1);
//            dbscan.setMinPoints(1);
//            dbscan.buildClusterer(instances);
//            System.out.println(dbscan.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e.getMessage());
//        }

//
//        Map<Integer, String> trainingData = new HashMap<>();
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("training_data_content.txt").getFile()));
//            String currentLine;
//            while ((currentLine = br.readLine()) != null) {
//                String[] values = currentLine.split("\\t");
//                trainingData.put(Integer.parseInt(values[0]), String.join(" ", Arrays.copyOfRange(values, 1, values.length)));
//            }
////            for (int i : trainingData.keySet()) {
////                System.out.println(trainingData.get(i));
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        FastVector training = new FastVector();
//        training.addElement(new Attribute("dunno", new FastVector()));
//
//        Instances dataSet = new Instances("Training_Data", training, 1000);
//        Instance inst = new Instance(1);
//        Attribute a1 = dataSet.attribute("dunno");
//        for (int i : trainingData.keySet()) {
//            inst.setValue(a1, trainingData.get(i));
//            dataSet.add(inst);
//        }
//
//        System.out.println(dataSet.toString());

    }

    public Issue generateCluster(String input) {
        return null;
    }

    public List<Issue> retrieveIssuesOrderedByPriority() {
        List<Issue> issues = dBContext.retrieveAllIssues();
        if (issues != null) {
            issues.sort(Comparator.comparingInt(Issue::getPriority));
            Collections.reverse(issues);
        }
        return issues;
    }

    public List<Question> retrieveUnassignedQuestions() {
        return  dBContext.retrieveUnassignedQuestions();
    }

    public void addQuestion(Issue issue, Question question) {
        issue.addQuestion(question);
        dBContext.updateIssue(issue);
        dBContext.assignQuestion(Long.toString(question.getQuestionID()));
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
            dBContext.deleteIssue(issue);
            removeIssueFromAssignedDevelopers(issue);
        } else {
            dBContext.updateIssue(issue);
            dBContext.unAssignQuestion(Long.toString(question.getQuestionID()));
        }
    }

    public void deleteIssue(Issue issue) {
        dBContext.deleteIssue(issue);
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
            firebaseAdapter.saveNewIssue(newIssue);
        }
    }

    public void assignIssue(Administrator admin, Issue issue, Developer dev) {
        checkAdminAndDeveloperExist(admin, dev);
        if (dBContext.getIssue(issue.getId()) != null) {
            issue.addAssignee(dev);
            dBContext.updateIssue(issue);
            dev.addIssue(issue);
            dBContext.saveUser(dev);
        } else {
            throw new IssueNotFoundException();
        }
    }

    public void assignIssue(Issue issue, Developer dev) {
        Developer developer = (Developer) dBContext.getUser(dev.getEmail());
        if (dev == null) {
            throw new UserException("Developer not found!");
        }

        issue.addAssignee(developer);
        developer.addIssue(issue);
        dBContext.saveUser(developer);
        dBContext.updateIssue(issue);
    }

    public void unAssignIssue(Administrator admin, Issue issue, Developer dev) {
        checkAdminAndDeveloperExist(admin, dev);
        if (dBContext.getIssue(issue.getId()) != null) {
            issue.removeAssignee(dev);
            dBContext.updateIssue(issue);
            dev.removeIssue(issue);
            dBContext.saveUser(dev);
        } else {
            throw new IssueNotFoundException();
        }
    }

    public void unAssignIssue(Issue issue, Developer dev) {
        Developer developer = (Developer) dBContext.getUser(dev.getEmail());
        if (developer == null) {
            throw new UserException("Developer not found!");
        }

        issue.removeAssignee(dev);
        dBContext.updateIssue(issue);
        dev.removeIssue(issue);
        dBContext.saveUser(dev);
    }

    public void resolveIssue(Developer dev, Issue issue) {
        User developer = dBContext.getUser(dev.getEmail());
        if (developer == null || developer instanceof Administrator) {
            throw new UserException("Developer not found!");
        }

        if (dBContext.getIssue(issue.getId()) != null) {
            issue.resolve(dev);
            dBContext.updateIssue(issue);
            removeIssueFromAssignedDevelopers(issue);
        } else {
            throw new IssueNotFoundException();
        }

    }

    private void checkAdminAndDeveloperExist(Administrator admin, Developer dev) {
        User administrator = dBContext.getUser(admin.getEmail());
        if (administrator == null) {
            throw new UserException("Admin not found!");
        }

        User developer = dBContext.getUser(dev.getEmail());
        if (developer == null) {
            throw new UserException("Developer not found!");
        }
    }

    private void removeIssueFromAssignedDevelopers(Issue issue) {
        if (issue.getAssignees().size() > 0) {
            for (String email : issue.getAssignees()) {
                User developer = dBContext.getUser(email);
                if (developer != null) {
                    ((Developer) developer).removeIssue(issue);
                    dBContext.saveUser(developer);
                }
            }
        }
    }
}
