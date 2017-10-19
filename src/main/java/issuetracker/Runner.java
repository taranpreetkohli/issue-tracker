package issuetracker;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.AuthenticationManager;
import issuetracker.cli.CLIManager;
import issuetracker.clustering.ClusterManager;
import issuetracker.db.FirebaseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Runner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) throws IOException {
        //new CLIManager(new AuthenticationManager(new FirebaseAdapter()), new ClusterManager()).loginCLI();

        FirebaseAdapter firebaseAdapter = new FirebaseAdapter();
        Administrator administrator = new Administrator("admin@gmail.com", "password");
        firebaseAdapter.registerUser(administrator);
    }

}
