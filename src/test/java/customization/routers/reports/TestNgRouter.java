package customization.routers.reports;

import br.com.erbium.core.EItem;
import br.com.erbium.core.EType;
import br.com.erbium.core.ReportRouter;
import br.com.erbium.core.TargetOutput;
import org.testng.Reporter;

public class TestNgRouter implements ReportRouter {

    int targetOutput = TargetOutput.REPORT;


    @Override
    public void setTargetOutput(int i) {
        targetOutput = i;
    }

    @Override
    public int getTargetOutput() {
        return targetOutput;
    }

    @Override
    public void route(EType eType, EItem eItem, String s) {
        String message = null;
        if (!eType.equals(EType.INFO)) {
            message += eType;
        }
        message += s;
        Reporter.log(s, true);
    }

    @Override
    public void route(String s) {
        route(EType.INFO, EItem.MESSAGE, s);
    }

    @Override
    public void route(EType eType, String s) {
       route(eType, EItem.MESSAGE, s);
    }

    @Override
    public void route(EItem eItem, String s) {
        route(EType.INFO, eItem, s);
    }

    @Override
    public String getName() {
        return "TestNG Report";
    }
}
