package repositories.repo1.cucumber.fakeStore.steps;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import repositories.repo1.cucumber.fakeStore.FakeStoreApiContext;

/**
 * This class contains Cucumber hooks, which are blocks of code that run
 * at various points in the Cucumber execution cycle.
 */
public class Hooks {

    /**
     * This method is executed before each individual scenario.
     * It's an ideal place for setup logic that needs to be run
     * for every test case, such as:
     * - Initializing a web driver
     * - Setting up database connections or resetting state
     * - Clearing test data from a previous run
     *
     * @param scenario The current scenario being executed, providing access to runtime
     *                 information like its name and tags.
     */
    @Before
    public void setupBeforeScenario(Scenario scenario) {
        System.out.println("--- HOOK: Before Scenario -> " + scenario.getName() + " ---");

        // Let's create a new workspace from scratch every time we run a scenario
        // so to make sure we're starting with a clean state
        FakeStoreApiContext.getNewInstance().createWorkspace(scenario);
    }
}