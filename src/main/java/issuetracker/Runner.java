package issuetracker;

import issuetracker.clustering.ClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    public static void main(String... args) throws Exception {
        System.out.println("Hello world!");
        ClusterManager.getInstance();
    }

}
