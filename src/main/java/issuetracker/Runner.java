package issuetracker;

import weka.clusterers.DBSCAN;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;

public class Runner {

    public static void main(String... args) {

        System.out.println("Hello world!");

        String file = System.getProperty("user.dir") + "\\resources\\singleForumPost.csv";
        System.out.println(file);

        CSVLoader loader = new CSVLoader();
        try {
            loader.setSource(new File(file));
            Instances data = loader.getDataSet();

            DBSCAN dbscan = new DBSCAN();
            dbscan.setEpsilon(0.12);
            dbscan.setMinPoints(5);
            dbscan.buildClusterer(data);

            System.out.println(dbscan.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
