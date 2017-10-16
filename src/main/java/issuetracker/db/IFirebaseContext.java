package issuetracker.db;

import com.google.firebase.database.DatabaseReference;
import issuetracker.util.Callback;

public interface IFirebaseContext {

    <T> IFirebaseContext read(DatabaseReference ref, Class<T> type, Callback<T> callback);

    <T> IFirebaseContext write(DatabaseReference ref, T object);

}
