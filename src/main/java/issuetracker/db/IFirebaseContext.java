package issuetracker.db;

import com.google.firebase.database.DatabaseReference;
import issuetracker.util.Callback;

public interface IFirebaseContext {

    <T> T read(DatabaseReference ref, Class<T> type);

    <T> IFirebaseContext write(DatabaseReference ref, T object);

    DatabaseReference getRoot();

}
