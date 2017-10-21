package issuetracker.exception;

public class IssueNotFoundException extends RuntimeException {

    public IssueNotFoundException(){}
    public IssueNotFoundException(String s){
        super(s);
    }


}
