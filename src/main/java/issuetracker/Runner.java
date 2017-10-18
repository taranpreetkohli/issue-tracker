package issuetracker;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;
import issuetracker.cli.CLIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Runner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    @Command(description="Command description")
    public String test(
            @Param(name="param1", description="Description of param1")
                    int param1,
            @Param(name="param2", description="Description of param2")
                    int param2) {

        return "Hello Testing";
    }

    public static void main(String[] args) throws IOException {
        ShellFactory.createConsoleShell("hello", "", new Runner())
                .commandLoop();

    }

}
