package repositories.repo1.cucumber.fakeStore.steps;

import admin.common.factories.MasterFakeStoreFactory;
import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Endpoint;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import repositories.repo1.cucumber.fakeStore.FakeStoreApiContext;

import static admin.common.factories.MasterFakeStoreFactory.LOGIN;

public class BasicTestSteps {

    private final FakeStoreApiContext fakeStoreApiContext;

    public BasicTestSteps(FakeStoreApiContext fakeStoreApiContext) {
        this.fakeStoreApiContext = fakeStoreApiContext;
    }

    @Given("the test data is set up")
    public void theTestDataIsSetUp() {
        fakeStoreApiContext.getFakeStoreCollection()
                .set("{{access-token}}", fakeStoreApiContext.getJwtTokenGenerator().generateToken())
                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_");
    }

    @When("I submit the endpoints")
    public void iSubmitTheEndpoints() {
        fakeStoreApiContext.getFakeStoreCollection()
                .e$(LOGIN).select()
                .e$(MasterFakeStoreFactory.GET_PRODUCTS).select()
                .e$(MasterFakeStoreFactory.GET_CATEGORIES).select()
                .e$(LOGIN)
                .qrset("token", "$.token").backToCollection()
                .selectedEndpoints(Endpoint::submit);
    }

    @Then("the results are displayed")
    public void theResultsAreDisplayed() {
        fakeStoreApiContext.getFakeStoreCollection()
                .selectedEndpoints(endpoint -> {
                    Assertions.assertTrue(endpoint.getResponseScript(CheckStatusCode.class).isStatusCodeAnyOf(200, 201)
                    );
                });
    }
}