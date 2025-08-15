package repositories.repo2.junit.fakeStore;

import admin.common.scripts.responses.CheckStatusCode;
import admin.fakeStore.scripts.responses.SchemaValidation;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import customization.factories.TeamFakeStoreFactory;
import repositories.common.fakeStore.FakeStoreSchemas;
import user.utils.JwtTokenGenerator;

import static admin.common.factories.MasterFakeStoreFactory.*;


public class PostmanInternetBddStyleFactory {

    TeamFakeStoreFactory teamFakeStoreFactory = new TeamFakeStoreFactory();
    JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();
    Workspace workspace;

    @Test
    public void test1() {

        workspace = teamFakeStoreFactory.createWorkspace();

        workspace
                .c$(FAKESTORE_COLLECTION)

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

                .then("check status codes and schema", (collection) -> {
                    collection.selectedEndpoints(endpoint -> {
                        String endpointName = endpoint.name();
                        Assertions.assertTrue(endpoint.getResponseScript(CheckStatusCode.class).isStatusCodeAnyOf(200, 201));
                        endpoint.getResponseScript(SchemaValidation.class).assertSchema();
                    });
                });
    }

    @Test
    public void test2() {
        workspace = teamFakeStoreFactory.createWorkspace();
        workspace
                .c$(FAKESTORE_COLLECTION)

                .given("basic setup", (collection) -> {
                    collection
                            .set("{{access-token}}", jwtTokenGenerator.generateToken())
                            .set("host", "https://fakestoreapi.com")
                            .set("userName", "xpto")
                            .set("userPassword", "incorrect")

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
    }
}
