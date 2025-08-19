package customization.factories;

import br.com.erbium.core.*;
import io.cucumber.java.Scenario;
import lombok.Getter;
import customization.routers.reports.CucumberRouter;


public class TeamFakeStoreCucumberFactory {

    /**
     * Creates a workspace customized for Cucumber out of team's factory.
     */

    @Getter
    Workspace workspace;
    Routers routers;

    public Workspace createWorkspace(Scenario scenario) {

        OutputConfig config = new OutputConfig(); // creates and sets WHAT is logged (all by default, then:)
//                .set(TargetOutput.NONE, EItem.ENVIRONMENT_TABLE) // remove item from destinations
//                .set(TargetOutput.CONSOLE, EItem.REQUEST_METHOD, EItem.REQUEST_URL); // and leave some items for console

        routers = new Routers(config); // create router container
        routers.add(new DefaultConsoleRouter()); // add first report router (console in this case)
        routers.add(new CucumberRouter(scenario)); // add second report router (Cucumber in this case)

        TeamFakeStoreFactory teamFactory = new TeamFakeStoreFactory(); // create team's factory
        workspace = teamFactory.createWorkspace();
        workspace.setRouters(routers);

        return workspace;
    }

}
