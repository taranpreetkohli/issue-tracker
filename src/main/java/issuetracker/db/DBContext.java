package issuetracker.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class DBContext {

    private static DBContext instance;

    private DBContext() {
        try {
            URL firebaseResourceURL = getClass().getClassLoader().getResource("firebase.json");
            if (firebaseResourceURL == null) {
                throw new RuntimeException("Firebase credentials file not found!");
            }
            File firebaseJSON = new File(firebaseResourceURL.getFile());
            FileInputStream serviceAccount = FileUtils.openInputStream(firebaseJSON);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://clustering754.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static DBContext getInstance() {
        if (instance == null) {
            instance = new DBContext();
        }

        return instance;
    }

    public FirebaseDatabase getFirebase() {
        return FirebaseDatabase.getInstance();
    }

    public DatabaseReference getRoot() {
        return FirebaseDatabase.getInstance().getReference();
    }


}
