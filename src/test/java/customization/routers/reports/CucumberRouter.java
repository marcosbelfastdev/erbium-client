package customization.routers.reports;

import br.com.erbium.core.EItem;
import br.com.erbium.core.EType;
import br.com.erbium.core.ReportRouter;
import io.cucumber.java.Scenario;

public class CucumberRouter implements ReportRouter {

    Scenario scenario;
    int targetOutput;

    public CucumberRouter(Scenario scenario) {
        this.scenario = scenario;
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
    public void route(EType eType, EItem eItem, String s) {
        scenario.log(eType.toString() + " " + eItem.toString() + " " + s);
    }

    @Override
    public void route(String s) {
        scenario.log(s);
    }

    @Override
    public void route(EType eType, String s) {
        scenario.log(eType.toString() + " " + s);
    }

    @Override
    public void route(EItem eItem, String s) {
        scenario.log(eItem.toString() + " " + s);
    }

    @Override
    public String getName() {
        return "Cucumber Report";
    }
}
