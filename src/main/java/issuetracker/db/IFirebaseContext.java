package issuetracker.db;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public interface IFirebaseContext {

    <T> T read(DatabaseReference ref, Class<T> type);

    <T> IFirebaseContext write(DatabaseReference ref, T object);

    DatabaseReference getRoot();

    IFirebaseContext deleteValue(DatabaseReference ref);

    <T> List<T> readChildren(DatabaseReference issuesRef, Class<T> tClass);
}
