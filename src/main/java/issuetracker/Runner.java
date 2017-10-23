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
        Question q = new Question("44330,\"xero-php API, how to structure query\",01/06/2017,Rajiv Pardiwala,2286,\"I'm try to request bank transactions from a specific account from a certain date using the xero-php API.$xero = new PrivateApplication($config);print_r(  $xero->load('Accounting\\BankTransaction')->setParameter('AccountID','XXXXXXX-4097-4028-9b99-XXXXXXXXX')->fromDate(new DateTime('2016-06-01 00:00:00'))->execute());which becomes this:\"\"https://api.xero.com/api.xro/2.0/BankTransactions?AccountID=XXXXXXX-4097-4028-9b99-XXXXXXXXX&fromDate=2016-06-01\"\"however, it is not filtering by AccountID and instead returns transactions for both of my accounts.  The fromDate is ignored as well.  What is the correct url to do what I want?\",https://community.xero.com/developer/discussion/50795838/");Question q1 = new Question("44333,Link Manual Journal Entry Transactions to an Invoice or Bill,30/04/2017,Technical Support,2286,\"We are making manual journal entries via the API... This is feeding the income statement and P&L correctly and now we want to link a series of manual journal entries to an Invoice or a Bill. Is there a way to accomplish this? For context... In our case, we provide level-of-effort cost-plus-fixed-fee services so we have one invoice per month containing a series of manual journal entries entered one or more times a day. We are submitting the journal entries via the API since there are too many to do this manually.\",https://community.xero.com/developer/discussion/48619929/");
        IssueManager im = new IssueManager(new FirebaseAdapter());
        LinkedList<Question> lq = new LinkedList<>();
        lq.add(q);
        lq.add(q1);
        System.out.println(Question.toARFF(lq));
        im.generateCluster(lq);
    }

}
