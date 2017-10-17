package issuetracker;

import issuetracker.authentication.Developer;
import issuetracker.db.FirebaseAdapter;

public class Runner {

    public static void main(String... args) {
        System.out.println("Hello world!");
        FirebaseAdapter db = new FirebaseAdapter();
        db.registerUser(new Developer("adar303@aucklanduni.ac.nz", "password"));
    }

}
