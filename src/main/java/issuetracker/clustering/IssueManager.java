package issuetracker.clustering;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssueManager {

    private static Logger logger = LoggerFactory.getLogger(IssueManager.class);

    public IssueManager() {
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


        Map<Integer, String> trainingData = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("training_data_content.txt").getFile()));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] values = currentLine.split("\\t");
                trainingData.put(Integer.parseInt(values[0]), String.join(" ", Arrays.copyOfRange(values, 1, values.length)));
            }
//            for (int i : trainingData.keySet()) {
//                System.out.println(trainingData.get(i));
//            }
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

    }

    public Issue generateCluster(String input) {
        return null;
    }

    public void addQuestion(Issue issue, Question question) {
        // add question to cluster
        // update DB
    }

    public void removeQuestion(Issue issue, Question question) {
        // remove question from cluster
        // update DB
    }
}
