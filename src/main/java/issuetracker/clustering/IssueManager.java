package issuetracker.clustering;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.Developer;
import issuetracker.authentication.User;
import issuetracker.db.FirebaseAdapter;
import issuetracker.exception.IssueNotFoundException;
import issuetracker.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.clusterers.DBSCAN;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class IssueManager {

    private static Logger logger = LoggerFactory.getLogger(IssueManager.class);
    private FirebaseAdapter firebaseAdapter;

    public IssueManager(FirebaseAdapter firebaseAdapter) {
        this.firebaseAdapter = firebaseAdapter;
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

    public List<Issue> generateCluster(String s){
        return null;
    }

    public List<Issue> generateCluster(List<Question> questions) {
//        System.out.println(Question.toARFF(questions));

        Instances instances = null;
        try {
            instances = new Instances(new StringReader(Question.toARFF(questions)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            StringToWordVector s = new StringToWordVector();
            s.setInputFormat(instances);
            s.setIDFTransform(true);
            instances = Filter.useFilter(instances, s);

            DBSCAN dbscan = new DBSCAN();
            dbscan.setEpsilon(1);
            dbscan.setMinPoints(1);
            dbscan.buildClusterer(instances);
            System.out.println(dbscan.toString());
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return null;

    }

    public List<Issue> retrieveIssuesOrderedByPriority() {
        List<Issue> issues = firebaseAdapter.retrieveAllIssues();
        issues.sort(Comparator.comparingInt(Issue::getPriority));
        Collections.reverse(issues);
        return issues;
    }

    public void addQuestion(Issue issue, Question question) {
        issue.addQuestion(question);
        firebaseAdapter.updateIssue(issue);
    }

    public void removeQuestion(Issue issue, Question question) {
        issue.removeQuestion(question);
        if (issue.getQuestions().size() == 0) {
            firebaseAdapter.deleteIssue(issue);
            removeIssueFromAssignedDevelopers(issue);
        } else {
            firebaseAdapter.updateIssue(issue);
        }
    }

    public void deleteIssue(Issue issue) {
        firebaseAdapter.deleteIssue(issue);
        removeIssueFromAssignedDevelopers(issue);
    }

    public void assignIssue(Administrator admin, Issue issue, Developer dev) {
        checkAdminAndDeveloperExist(admin, dev);
        if (firebaseAdapter.getIssue(issue.getId()) != null) {
            issue.addAssignee(dev);
            firebaseAdapter.updateIssue(issue);
            dev.addIssue(issue);
            firebaseAdapter.saveUser(dev);
        } else {
            throw new IssueNotFoundException();
        }
    }

    public void unAssignIssue(Administrator admin, Issue issue, Developer dev) {
        checkAdminAndDeveloperExist(admin, dev);
        if (firebaseAdapter.getIssue(issue.getId()) != null) {
            issue.removeAssignee(dev);
            firebaseAdapter.updateIssue(issue);
            dev.removeIssue(issue);
            firebaseAdapter.saveUser(dev);
        } else {
            throw new IssueNotFoundException();
        }
    }

    public void resolveIssue(Developer dev, Issue issue) {
        User developer = firebaseAdapter.getUser(dev.getEmail());
        if (developer == null || developer instanceof Administrator) {
            throw new UserException("Developer not found!");
        }

        if (firebaseAdapter.getIssue(issue.getId()) != null) {
            issue.resolve(dev);
            firebaseAdapter.updateIssue(issue);
            removeIssueFromAssignedDevelopers(issue);
        } else {
            throw new IssueNotFoundException();
        }

    }

    private void checkAdminAndDeveloperExist(Administrator admin, Developer dev) {
        User administrator = firebaseAdapter.getUser(admin.getEmail());
        if (administrator == null) {
            throw new UserException("Admin not found!");
        }

        User developer = firebaseAdapter.getUser(dev.getEmail());
        if (developer == null) {
            throw new UserException("Developer not found!");
        }
    }

    private void removeIssueFromAssignedDevelopers(Issue issue) {
        if (issue.getAssignees().size() > 0) {
            for (Developer developer : issue.getAssignees()) {
                developer.removeIssue(issue);
                firebaseAdapter.saveUser(developer);
            }
        }
    }
}
