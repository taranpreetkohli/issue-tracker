package issuetracker;

import issuetracker.authentication.User;
import issuetracker.db.FirebaseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    public static void main(String... args) {
        System.out.println("Hello world!");
        FirebaseAdapter db = new FirebaseAdapter();
        User user = db.getUser("tkoh638@aucklanduni.ac.nz");
        logger.error(user.getEmail());

        User e = db.getUser("asdfasdf");
        System.out.println(e + "asdfasdf");
    }

}
