package issuetracker.clustering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterManager {

    private static Logger logger = LoggerFactory.getLogger(ClusterManager.class);

    public ClusterManager() {
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
