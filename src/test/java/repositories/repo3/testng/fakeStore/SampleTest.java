package repositories.repo3.testng.fakeStore;

import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import repositories.repo3.testng.testngFactories.TestNgMainFactory;
import user.utils.JwtTokenGenerator;

import java.time.Duration;

import static admin.common.factories.MasterFakeStoreFactory.*;
import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_GET_CATEGORIES;
import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_GET_PRODUCTS;
import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_LOGIN;

public class SampleTest extends TestNgHooks {


    @Test(priority = 1)
    public void testOne() {
        Workspace workspace = testNgMainFactory.createWorkspace();
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace
                // Create empty collection
                .addCollection(FAKESTORE_COLLECTION)

                // Import from Postman
                // You need to import postman collection json in 'resources' to Postman first
                // and customize UID and API KEY

                // This imports by hitting and downloading the postman collection from Postman servers
                // Note the Duration parameter. Remove it or set it to a few seconds to always download from Postman
                // else it will hit the cached json file in user profile/.erbium/ directory
                .importPostManCollection(FAKESTORE_COLLECTION_UID, FAKESTORE_POSTMAN_API_KEY, Duration.ofDays(365))

                // Select some endpoints for batch submission later (order is important)
                .e$(FAKESTORE_LOGIN).select()
                .e$(FAKESTORE_GET_PRODUCTS).select()
                .e$(FAKESTORE_GET_CATEGORIES).select()

                // Set the collection environment variables required
                .set("{{access-token}}", jwtTokenGenerator.generateToken())
                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_")

                .e$(FAKESTORE_LOGIN)
                // Force the variable token to get the value of token property after response
                .qrset("token", "$.token").backToCollection()

                // submit all selected endpoints in one go
                .selectedEndpoints(Endpoint::submit);
    }

    @Test(priority = 2)
    public void testTwo() {
        Workspace workspace = testNgMainFactory.createWorkspace();
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace
                .addCollection(FAKESTORE_COLLECTION)
                .importPostManCollection(FAKESTORE_COLLECTION_UID, FAKESTORE_POSTMAN_API_KEY, Duration.ofDays(365))

                .given("basic setup", (collection) -> {
                    collection
                            // Set the collection environment variables required
                            .set("{{access-token}}", jwtTokenGenerator.generateToken())
                            .set("host", "https://fakestoreapi.com")
                            .set("userName", "mor_2314")
                            .set("userPassword", "83r5^_")
                            // Select some endpoints for batch submission later (order is important)
                            .e$(FAKESTORE_LOGIN).select()
                            .e$(FAKESTORE_GET_PRODUCTS).select()
                            .e$(FAKESTORE_GET_CATEGORIES).select()

                            .e$(FAKESTORE_LOGIN)
                            // Force the variable token to get the value of token property after response
                            .qrset("token", "$.token").backToCollection()
                            .getEndpoints().forEach(endpoint -> {
                                endpoint.addResponseScript("Check status code", CheckStatusCode.class);
                            });
                })

                .when("submit all", (collection) -> {
                    collection.selectedEndpoints(Endpoint::submit);
                })

                .then("check status codes", (collection) -> {
                    collection.selectedEndpoints(endpoint -> {
                        Assertions.assertTrue(endpoint.getResponseScript("Check status code", CheckStatusCode.class).isStatusCodeAnyOf(200, 201)
                        );
                    });
                });
    }
}