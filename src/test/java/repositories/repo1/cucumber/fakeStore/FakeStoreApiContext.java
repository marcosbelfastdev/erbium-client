package repositories.repo1.cucumber.fakeStore;

import admin.common.factories.MasterFakeStoreFactory;

import br.com.erbium.core.Collection;
import br.com.erbium.core.Workspace;
import lombok.Getter;
import user.factories.TeamFakeStoreCucumberFactory;
import user.utils.JwtTokenGenerator;

/**
 * A simple container for sharing state between steps in a single scenario.
 * PicoContainer will create a new instance of this class for each scenario
 * and inject it into the constructor of any step definition class that needs it.
 */
public class FakeStoreApiContext {

    @Getter
    Workspace workspace;

    JwtTokenGenerator jwtTokenGenerator;

    public void createWorkspace() {
        TeamFakeStoreCucumberFactory teamFakeStoreCucumberFactory = new TeamFakeStoreCucumberFactory();
        workspace = teamFakeStoreCucumberFactory.createWorkspace();
        jwtTokenGenerator = new JwtTokenGenerator();
    }


    public Collection getFakeStoreCollection() {
        return workspace.getCollection(MasterFakeStoreFactory.FAKESTORE_COLLECTION);
    }

    public JwtTokenGenerator getJwtTokenGenerator() {
        return jwtTokenGenerator == null ? jwtTokenGenerator = new JwtTokenGenerator() : jwtTokenGenerator;

    }

}
