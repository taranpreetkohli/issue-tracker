package issuetracker.clustering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.clusterers.DBSCAN;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

public class ClusterManager {

    private static Logger logger = LoggerFactory.getLogger(ClusterManager.class);

    public ClusterManager() {
        try {
            URL testResourceURL = getClass().getClassLoader().getResource("test.arff");
            File testFile = new File(testResourceURL.getFile());
            Instances instances = new Instances(new BufferedReader(new FileReader(testFile)));
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
    }

    public Cluster generateCluster(String input) {
        return null;
    }

    public void addQuestion(Cluster cluster, Question question) {
        // add question to cluster
        // update DB
    }

    public void removeQuestion(Cluster cluster, Question question) {
        // remove question from cluster
        // update DB
    }
}
