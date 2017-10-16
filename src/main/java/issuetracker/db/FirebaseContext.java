package issuetracker.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class FirebaseContext implements IFirebaseContext {

    private static Logger logger = LoggerFactory.getLogger(FirebaseContext.class);

    private static FirebaseContext instance;

    private FirebaseContext() {
        try {
            URL firebaseResourceURL = getClass().getClassLoader().getResource("firebase.json");
            if (firebaseResourceURL == null) {
                logger.error("Firebase credentials file not found!");
                throw new RuntimeException("Firebase credentials file not found!");
            }
            File firebaseJSON = new File(firebaseResourceURL.getFile());
            FileInputStream serviceAccount = FileUtils.openInputStream(firebaseJSON);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://clustering754.firebaseio.com/")
                    .build();

            logger.info("Initialising Firebase...");
            FirebaseApp.initializeApp(options);
            logger.info("Firebase correctly initialised...");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static IFirebaseContext getInstance() {
        if (instance == null) {
            instance = new FirebaseContext();
        }

        return instance;
    }

    @Override
    public <T> IFirebaseContext read(DatabaseReference ref, Class<T> type, Callback<T> callback) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                T instance = snapshot.getValue(type);
                callback.onCompleted(instance);
                countDownLatch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });

        try {
            countDownLatch.await();
            return instance;
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public <T> IFirebaseContext write(DatabaseReference ref, T object) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ref.setValue(object, (error, ref1) -> countDownLatch.countDown());

        try {
            countDownLatch.await();
            return instance;
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
