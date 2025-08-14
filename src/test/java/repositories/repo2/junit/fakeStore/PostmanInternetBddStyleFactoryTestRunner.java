package repositories.repo2.junit.fakeStore;

import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.TestRunner;
import br.com.erbium.core.Workspace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import user.factories.TeamFakeStoreFactory;
import user.utils.JwtTokenGenerator;

import static admin.common.factories.MasterFakeStoreFactory.*;


public class PostmanInternetBddStyleFactoryTestRunner {

    TeamFakeStoreFactory teamFakeStoreFactory = new TeamFakeStoreFactory();
    JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();
    Workspace workspace;
    TestRunner testRunner = new TestRunner();

    @Test
    public void test1() {
        testRunner
        .workspace(teamFakeStoreFactory.createWorkspace())
                .withIterations(10).execute((context) -> {

            Workspace workspace = context.workspace();
            workspace.c$(FAKESTORE_COLLECTION)

                    .given((collection) -> {
                        collection
                                .set("{{access-token}}", jwtTokenGenerator.generateToken())
                                .set("host", "https://fakestoreapi.com")
                                .set("userName", "mor_2314")
                                .set("userPassword", "83r5^_")

                                .e$(FAKESTORE_LOGIN).select()
                                .e$(FAKESTORE_GET_PRODUCTS).select()
                                .e$(FAKESTORE_GET_CATEGORIES).select()

                                .e$(FAKESTORE_LOGIN)
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
        });
    }
}
