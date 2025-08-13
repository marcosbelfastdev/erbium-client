package repositories.repo1.cucumber.fakeStore.steps;

import admin.common.factories.MasterFakeStoreFactory;
import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Endpoint;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import repositories.repo1.cucumber.fakeStore.FakeStoreApiContext;

public class BasicTestBddStyleSteps {

    private final FakeStoreApiContext fakeStoreApiContext;

    public BasicTestBddStyleSteps(FakeStoreApiContext fakeStoreApiContext) {
        this.fakeStoreApiContext = fakeStoreApiContext;
    }

    @Given("the test data is set up")
    public void theTestDataIsSetUp() {
        fakeStoreApiContext.getFakeStoreCollection()
                .given((collection) -> {
                    collection

                            .set("{{access-token}}", fakeStoreApiContext.getJwtTokenGenerator().generateToken())
                            .set("host", "https://fakestoreapi.com")
                            .set("userName", "mor_2314")
                            .set("userPassword", "83r5^_")

                            .e$(MasterFakeStoreFactory.LOGIN).select()
                            .e$(MasterFakeStoreFactory.GET_PRODUCTS).select()
                            .e$(MasterFakeStoreFactory.GET_CATEGORIES).select()
                            .e$(MasterFakeStoreFactory.LOGIN)
                            .qrset("token", "$.token");
                });
    }

    @When("I submit the endpoints")
    public void iSubmitTheEndpoints() {
        fakeStoreApiContext.getFakeStoreCollection()
                .when((collection) -> {
                    collection
                            .selectedEndpoints(Endpoint::submit);
                });
    }

    @Then("the results are displayed")
    public void theResultsAreDisplayed() {
        fakeStoreApiContext.getFakeStoreCollection()
                .selectedEndpoints(endpoint -> {
                    Assertions.assertTrue(endpoint.getResponseScript(CheckStatusCode.class).isStatusCodeAnyOf(200)
                    );
                });
    }
}