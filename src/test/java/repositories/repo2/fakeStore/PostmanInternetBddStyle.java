package repositories.repo2.fakeStore;

import admin.common.factories.MasterFakeStoreFactory;
import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import user.utils.JwtTokenGenerator;

import java.time.Duration;

public class PostmanInternetBddStyle {

    @Test
    public void test() {

        Workspace workspace = new Workspace();
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace
                .addCollection(MasterFakeStoreFactory.FAKESTORE_API)
                .importPostManCollection(MasterFakeStoreFactory.UID, MasterFakeStoreFactory.KEY, Duration.ofDays(365))

                .given("basic setup", (collection) -> {
                    collection
                            // Set the collection environment variables required
                            .set("{{access-token}}", jwtTokenGenerator.generateToken())
                            .set("host", "https://fakestoreapi.com")
                            .set("userName", "mor_2314")
                            .set("userPassword", "83r5^_")
                            // Select some endpoints for batch submission later (order is important)
                            .e$(MasterFakeStoreFactory.LOGIN).select()
                            .e$(MasterFakeStoreFactory.GET_PRODUCTS).select()
                            .e$(MasterFakeStoreFactory.GET_CATEGORIES).select()

                            .e$(MasterFakeStoreFactory.LOGIN)
                            // Force the variable token to get the value of token property after response
                            .qrset("token", "$.token");
                })

                .when("submit all", (collection) -> {
                    collection.selectedEndpoints(Endpoint::submit);
                })

                .then("check status codes", (collection) -> {
                    collection.selectedEndpoints(endpoint -> {
                        Assertions.assertTrue(endpoint.getResponseScript(CheckStatusCode.class).isStatusCodeAnyOf(200, 201)
                        );
                    });
                });
    }
}
