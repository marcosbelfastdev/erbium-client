package customization.factories;

import admin.common.factories.MasterFakeStoreFactory;
import admin.fakeStore.scripts.responses.SchemaValidation;
import br.com.erbium.core.*;
import lombok.Getter;
import repositories.common.fakeStore.FakeStoreSchemas;

import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_COLLECTION;
import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_LOGIN;

public class TeamFakeStoreFactory {

    @Getter
    Workspace workspace;

    public Workspace createWorkspace() {
        workspace = MasterFakeStoreFactory.createWorkspace();
        // Add scripts that can be part of all tests for this team, or repository etc.
        Collection collection = workspace.c$(FAKESTORE_COLLECTION);

        // Adding response script for schema validation


        // Add triggers that will be called before and after each test
        return workspace;
    }

}
