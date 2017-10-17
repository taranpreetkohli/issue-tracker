package issuetracker.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import issuetracker.util.Callback;
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
    public DatabaseReference getRoot(){
        return FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public <T> T read(DatabaseReference ref, Class<T> type) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        final Object[] instance = new Object[1];
        logger.info("Reading value from reference with key: " + ref.getPath().toString());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                instance[0] = snapshot.getValue(type);
                logger.info("Successfully read value from reference with key: " + ref.getPath().toString());
                countDownLatch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                logger.error("Failure reading value from reference with key: " + ref.getPath().toString());
                throw new RuntimeException(error.getMessage());
            }
        });

        try {
            countDownLatch.await();
            return (T) instance[0];
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public <T> IFirebaseContext write(DatabaseReference ref, T object) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        logger.info("Writing value to reference with key: " + ref.getKey());
        ref.setValue(object, (error, ref1) -> {
            countDownLatch.countDown();
            if (error == null) {
                logger.info("Successfully written value to reference with key: " + ref.getKey());
            } else {
                logger.error(error.getMessage());
            }
        });

        try {
            countDownLatch.await();
            return instance;
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
