package repositories.repo2.junit.fakeStore;

import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import customization.utils.JwtTokenGenerator;

import java.time.Duration;

import static admin.common.factories.MasterFakeStoreFactory.*;

public class PostmanInternetBddStyle {

    @Test
    public void test() {

        Workspace workspace = new Workspace();
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
