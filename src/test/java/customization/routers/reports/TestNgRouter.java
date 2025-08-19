package customization.routers.reports;

import br.com.erbium.core.LogItem;
import br.com.erbium.core.LogType;
import br.com.erbium.core.ReportRouter;
import br.com.erbium.core.TargetOutput;
import org.testng.Reporter;

public class TestNgRouter implements ReportRouter {

    int targetOutput = TargetOutput.REPORT;


    @Override
    public void commit() {

    }

    @Override
    public void setTargetOutput(int i) {
        targetOutput = i;
    }

    @Override
    public int getTargetOutput() {
        return targetOutput;
    }

    @Override
    public void route(LogType LogType, LogItem LogItem, String s) {
        String message = null;
        if (!LogType.equals(LogType.INFO)) {
            message += LogType;
        }
        message += s;
        Reporter.log(s, true);
    }

    @Override
    public void route(String s) {
        route(LogType.INFO, LogItem.MESSAGE, s);
    }

    @Override
    public void route(LogType LogType, String s) {
       route(LogType, LogItem.MESSAGE, s);
    }

    @Override
    public void route(LogItem LogItem, String s) {
        route(LogType.INFO, LogItem, s);
    }

    @Override
    public String getName() {
        return "TestNG Report";
    }
}
