package issuetracker.db;

public interface Callback<T> {

    void onCompleted(T value);

}
