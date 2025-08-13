package repositories.repo2.fakeStore;

import admin.common.factories.MasterFakeStoreFactory;
import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import user.factories.TeamFakeStoreFactory;
import user.utils.JwtTokenGenerator;

public class PostmanInternetBddStyleFactory {

    TeamFakeStoreFactory teamFakeStoreFactory = new TeamFakeStoreFactory();
    JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();
    Workspace workspace;

    @Test
    public void test1() {

        workspace = teamFakeStoreFactory.createWorkspace();

        workspace
                .c$(MasterFakeStoreFactory.FAKESTORE_API)

                .given((collection) -> {
                    collection
                            .set("{{access-token}}", jwtTokenGenerator.generateToken())
                            .set("host", "https://fakestoreapi.com")
                            .set("userName", "mor_2314")
                            .set("userPassword", "83r5^_")

                            .e$(MasterFakeStoreFactory.LOGIN).select()
                            .e$(MasterFakeStoreFactory.GET_PRODUCTS).select()
                            .e$(MasterFakeStoreFactory.GET_CATEGORIES).select()

                            .e$(MasterFakeStoreFactory.LOGIN)
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

    @Test
    public void test2() {
        workspace = teamFakeStoreFactory.createWorkspace();
        workspace
                .c$(MasterFakeStoreFactory.FAKESTORE_API)

                .given("basic setup", (collection) -> {
                    collection
                            .set("{{access-token}}", jwtTokenGenerator.generateToken())
                            .set("host", "https://fakestoreapi.com")
                            .set("userName", "xpto")
                            .set("userPassword", "incorrect")

                            .e$(MasterFakeStoreFactory.LOGIN).select()
                            .e$(MasterFakeStoreFactory.GET_PRODUCTS).select()
                            .e$(MasterFakeStoreFactory.GET_CATEGORIES).select()

                            .e$(MasterFakeStoreFactory.LOGIN)
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
