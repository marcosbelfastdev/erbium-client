package customization.factories;

import admin.common.factories.MasterFakeStoreFactory;
import br.com.erbium.core.*;
import lombok.Getter;

public class TeamFakeStoreFactory {

    @Getter
    Workspace workspace;

    public Workspace createWorkspace() {
        workspace = MasterFakeStoreFactory.createWorkspace();
        // Add scripts that can be part of all tests for this team, or repository etc.
        // Add triggers that will be called before and after each test
        return workspace;
    }

}
