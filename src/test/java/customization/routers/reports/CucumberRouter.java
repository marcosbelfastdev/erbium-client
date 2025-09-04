package customization.routers.reports;

import br.com.erbium.core.LogItem;
import br.com.erbium.core.LogType;
import br.com.erbium.core.LogItem;
import br.com.erbium.core.LogType;
import br.com.erbium.core.ReportRouter;
import br.com.erbium.core.TargetOutput;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public class CucumberRouter implements ReportRouter {

    StringBuilder buffer = new StringBuilder();

    Scenario scenario;
    int targetOutput = TargetOutput.REPORT;

    public CucumberRouter(Scenario scenario) {
        this.scenario = scenario;
    }

    private void addToBuffer(LogType eType, LogItem eItem, String s) {

        buffer.append("\n"+ s);
        if (eItem == LogItem.TIMER || eType == LogType.END) {
            commit();
        }
        //buffer.append("\n"+ s);
    }

    @Override
    public void commit() {
        //scenario.log(buffer.toString());
        Allure.addAttachment("Allure Note", "text/plain",
                buffer.toString());
        buffer = new StringBuilder();
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
    public void route(LogType eType, LogItem eItem, String s) {
        addToBuffer(eType, eItem, s);
    }

    @Override
    public void route(String s) {
        addToBuffer(LogType.UDEF, LogItem.MESSAGE, s);
    }

    @Override
    public void route(LogType eType, String s) {
        addToBuffer(eType, LogItem.MESSAGE, s);
    }

    @Override
    public void route(LogItem eItem, String s) {
        addToBuffer(LogType.UDEF, eItem, s);
    }

    @Override
    public String getName() {
        return "Cucumber Report";
    }
}
