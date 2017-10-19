package issuetracker.exception;

public class IssueAlreadyResolved extends RuntimeException {

    public IssueAlreadyResolved(String message) {
        super(message);
    }

}
