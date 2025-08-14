package repositories.repo1.cucumber.fakeStore.steps;

import admin.common.factories.MasterFakeStoreFactory;
import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Collection;
import br.com.erbium.core.Endpoint;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import repositories.repo1.cucumber.fakeStore.FakeStoreApiContext;

import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_LOGIN;

public class BasicTestSteps {

    private final FakeStoreApiContext fakeStoreApiContext = FakeStoreApiContext.getInstance();

    @Given("the test data is set up")
    public void theTestDataIsSetUp() {
        Collection collection = fakeStoreApiContext.getFakeStoreCollection();
        collection.out().log("Setting environment variables...");
        collection
                .set("{{access-token}}", fakeStoreApiContext.getJwtTokenGenerator().generateToken())
                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_");
    }

    @When("I submit the endpoints")
    public void iSubmitTheEndpoints() {
        Collection collection = fakeStoreApiContext.getFakeStoreCollection();
        collection.out().log("Submitting endpoints...");
        collection
                .e$(FAKESTORE_LOGIN).select()
                .e$(MasterFakeStoreFactory.FAKESTORE_GET_PRODUCTS).select()
                .e$(MasterFakeStoreFactory.FAKESTORE_GET_CATEGORIES).select()
                .e$(FAKESTORE_LOGIN)
                .qrset("token", "$.token").backToCollection()
                .selectedEndpoints(Endpoint::submit);
    }

    @Then("the results are displayed")
    public void theResultsAreDisplayed() {
        Collection collection = fakeStoreApiContext.getFakeStoreCollection();
        collection.out().log("Checking status code...");
        collection
                .selectedEndpoints(endpoint -> {
                    Assertions.assertTrue(endpoint.getResponseScript(CheckStatusCode.class).isStatusCodeAnyOf(200, 201)
                    );
                    endpoint.out().log("Endpoint name asserted: " + endpoint.getName());
                });
    }
}