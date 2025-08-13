package user.factories;

import admin.common.factories.MasterFakeStoreFactory;
import br.com.erbium.core.*;
import lombok.Getter;
import user.routers.reports.FakeStoreCucumberRouter;

public class TeamFakeStoreFactory {

    @Getter
    Workspace workspace;

    public Workspace createWorkspace() {
        workspace = MasterFakeStoreFactory.createWorkspace();
        // Add scripts that can be part of all tests
        // Add triggers that will be called before and after each test
        return workspace;
    }

}
