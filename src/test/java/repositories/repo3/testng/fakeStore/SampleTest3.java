package repositories.repo3.testng.fakeStore;

import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import customization.utils.JwtTokenGenerator;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import static admin.common.factories.MasterFakeStoreFactory.*;

public class SampleTest3 extends TestNgHooks {


    @Test(priority = 1)
    public void testOne() {
        Workspace workspace = testNgMainFactory.createWorkspace();
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace.c$(FAKESTORE_COLLECTION)
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

        workspace.c$(FAKESTORE_COLLECTION)
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
                            .qrset("token", "$.token");
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

    @Test(priority = 3)
    public void testThree() {
        Workspace workspace = testNgMainFactory.createWorkspace();
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace.c$(FAKESTORE_COLLECTION)
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
                            .qrset("token", "$.token");
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