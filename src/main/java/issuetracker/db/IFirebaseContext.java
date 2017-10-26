package issuetracker.db;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Defines the instructions required by the system to interact with Google Firebase.
 *
 * @see com.google.firebase.database.FirebaseDatabase
 */
public interface IFirebaseContext {

    /**
     * Read from the database and retrieve and object of the type presented
     *
     * @param ref  DatabaseReference to check, this decides what level to do the retrieval on.
     * @param type The type expected to be returned from the reference, ensures Typed returns
     * @param <T>  The return type of the object.
     * @return An object of type <code>T</code> instansiated from the database.
     */
    <T> T read(DatabaseReference ref, Class<T> type);

    /**
     * Write an object to the database.
     *
     * @param ref    DatabaseReference determining what level to write to
     * @param object the object to write.
     * @param <T>    Object type to write.
     * @return Returns the context object for ease of use following "Method Chaining" design pattern.
     */
    <T> IFirebaseContext write(DatabaseReference ref, T object);

    /**
     * Get the rood of the current database connected to this sytem.
     *
     * @return DatabaseReference dictating the root of our database.
     */
    DatabaseReference getRoot();

    /**
     * Delete an object from the database at the defined reference position.
     *
     * @param ref Database level under which to delete.
     * @return Returns the context object for ease of use following "Method Chaining" design pattern.
     */
    IFirebaseContext deleteValue(DatabaseReference ref);

    /**
     * Read a list of values under a reference.
     *
     * @param ref    The database level under which to read
     * @param tClass the class of the object we wish to return
     * @param <T>    The type of the object expected to be returned
     * @return List of objects under a reference
     */
    <T> List<T> readChildren(DatabaseReference ref, Class<T> tClass);
}
