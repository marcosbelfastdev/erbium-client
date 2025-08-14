package repositories.repo3.testng.testngFactories;

import br.com.erbium.core.*;
import customization.factories.TeamFakeStoreFactory;
import customization.routers.reports.CucumberRouter;
import customization.routers.reports.TestNgRouter;
import lombok.Getter;

public class TestNgMainFactory {

    @Getter
    Workspace workspace;

    public Workspace createWorkspace() {

        // Creates the workspace based on Team factory
        TeamFakeStoreFactory teamFactory = new TeamFakeStoreFactory(); // create team's factory
        workspace = teamFactory.createWorkspace();

        // Now, let us customize it for TestNG

        // As TestNG normally logs to console and report, we will mute console here and leave it for TestNG report
        OutputConfig config = new OutputConfig() // creates and sets WHAT is logged (all by default, then:)
                .setAllItems(TargetOutput.REPORT);


        Routers routers = new Routers(config); // create router container
        routers.add(new TestNgRouter()); // add second report router (Cucumber in this case)

        workspace.setRouters(routers);
        return workspace;
    }

}
