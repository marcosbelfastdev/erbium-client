package user.routers.reports;

import br.com.erbium.core.EItem;
import br.com.erbium.core.EType;
import br.com.erbium.core.ReportRouter;

public class FakeStoreCucumberRouter implements ReportRouter {

    @Override
    public void setTargetOutput(int i) {

    }

    @Override
    public int getTargetOutput() {
        return 0;
    }

    @Override
    public void route(EType eType, EItem eItem, String s) {

    }

    @Override
    public void route(String s) {

    }

    @Override
    public void route(EType eType, String s) {

    }

    @Override
    public void route(EItem eItem, String s) {

    }

    @Override
    public String getName() {
        return "";
    }
}
