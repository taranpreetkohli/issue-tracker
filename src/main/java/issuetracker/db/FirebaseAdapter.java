package issuetracker.db;

import issuetracker.authentication.Administrator;
import issuetracker.authentication.Developer;
import issuetracker.authentication.IUser;
import issuetracker.util.Callback;

public class FirebaseAdapter {
    protected IFirebaseContext db = FirebaseContext.getInstance();

    public FirebaseAdapter registerUser(IUser newUser){
        // Map user to if they're admin or developer.
        db.write(db.getRoot().child("mappings").child(newUser.getEmail()), newUser.getClass().getSimpleName());

        // Write the object to the database
        db.write(db.getRoot().child(newUser.getClass().getSimpleName()).child(newUser.getEmail()), newUser);

        // Return this object for chaining
        return this;
    }

    public IUser getUser(String email){
        // Check mapping for this email
        final IUser[] user = new IUser[1];
        db.read(db.getRoot().child("mappings"), String.class, new Callback<String>() {
            @Override
            public void onCompleted(String value) {
                if(value.equals("Developer")){
                    db.read(db.getRoot().child(value).child(email), Developer.class, new Callback<Developer>() {
                        @Override
                        public void onCompleted(Developer value) {
                            user[0] = value;
                        }
                    });
                }else {
                    db.read(db.getRoot().child(value).child(email), Administrator.class, new Callback<Administrator>() {
                        @Override
                        public void onCompleted(Administrator value) {
                            user[0] = value;
                        }
                    });
                }
            }
        });
        return user[0];
    }



}
