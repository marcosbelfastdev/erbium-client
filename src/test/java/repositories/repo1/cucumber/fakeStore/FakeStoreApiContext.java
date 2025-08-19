package repositories.repo1.cucumber.fakeStore;

import admin.common.factories.MasterFakeStoreFactory;

import br.com.erbium.core.Collection;
import br.com.erbium.core.Workspace;
import io.cucumber.java.Scenario;
import lombok.Getter;
import customization.factories.TeamFakeStoreCucumberFactory;
import customization.utils.JwtTokenGenerator;

/**
 * A simple container for sharing state between steps in a single scenario.
 * PicoContainer will create a new instance of this class for each scenario
 * and inject it into the constructor of any step definition class that needs it.
 */
public class FakeStoreApiContext {

    public static ThreadLocal<FakeStoreApiContext> instance = new ThreadLocal<>();

    @Getter
    Workspace workspace;
    JwtTokenGenerator jwtTokenGenerator;

    public static FakeStoreApiContext getInstance() {
        return instance.get();
    }

    public static FakeStoreApiContext getNewInstance() {
        instance.set(new FakeStoreApiContext());
        return instance.get();
    }

    public void createWorkspace(Scenario scenario) {
        TeamFakeStoreCucumberFactory teamFakeStoreCucumberFactory = new TeamFakeStoreCucumberFactory();
        workspace = teamFakeStoreCucumberFactory.createWorkspace(scenario);
        jwtTokenGenerator = new JwtTokenGenerator();
    }


    public Collection getFakeStoreCollection() {
        return workspace.getCollection(MasterFakeStoreFactory.FAKESTORE_COLLECTION);
    }

    public JwtTokenGenerator getJwtTokenGenerator() {
        return jwtTokenGenerator == null ? jwtTokenGenerator = new JwtTokenGenerator() : jwtTokenGenerator;

    }

}
