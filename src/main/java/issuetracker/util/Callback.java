package issuetracker.util;

public interface Callback<T> {

    void onCompleted(T value);

}
