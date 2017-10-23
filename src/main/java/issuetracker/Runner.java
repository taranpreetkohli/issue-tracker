package issuetracker;

import issuetracker.clustering.IssueManager;
import issuetracker.clustering.Question;
import issuetracker.db.FirebaseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;

public class Runner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) throws IOException {
        Question q = new Question("44330,\"xero-php API, how to structure query\",01/06/2017,\"Rajiv Pardiwala\",2286,\"I'm try to request bank transactions from a specific account from a certain date using the xero-php API.$xero = new PrivateApplication($config);print_r(  $xero->load('Accounting\\BankTransaction')->setParameter('AccountID','XXXXXXX-4097-4028-9b99-XXXXXXXXX')->fromDate(new DateTime('2016-06-01 00:00:00'))->execute());which becomes this:\'https://api.xero.com/api.xro/2.0/BankTransactions?AccountID=XXXXXXX-4097-4028-9b99-XXXXXXXXX&fromDate=2016-06-01\'however, it is not filtering by AccountID and instead returns transactions for both of my accounts.  The fromDate is ignored as well.  What is the correct url to do what I want?\",\"https://community.xero.com/developer/discussion/50795838/\"");
        IssueManager im = new IssueManager(new FirebaseAdapter());
        LinkedList<Question> lq = new LinkedList<>();
        lq.add(q);
        im.generateCluster(lq);
    }

}
