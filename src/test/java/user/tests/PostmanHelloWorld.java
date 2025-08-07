package user.tests;

import base.components.core.Endpoint;
import base.components.core.Workspace;
import org.junit.jupiter.api.Test;
import user.utils.JwtTokenGenerator;

import static user.workspaces.collections.FakeStoreApiCollection.*;

public class PostmanHelloWorld {

    @Test
    public void test() {
        Workspace workspace = new Workspace();
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace
                .addCollection(FAKESTORE_API)
                .importPostManCollection(UID, KEY)

                .e$(GET_USERS).select()
                .e$(LOGIN).qrset("token", "$.token").select()
                .e$(GET_PRODUCTS).select()
                .e$(GET_CATEGORIES).select()

                .set("{{jwtToken}}", jwtTokenGenerator.generateToken())
                .set("host", "https://fakestoreapi.com")
                .set("{{userName}}", "mor_2314")
                .set("{{userPassword}}", "83r5^_")
                .set("{{bearer}}", "Bearer {{token}}")

                .selectedEndpoints(endpoint -> System.out.println(endpoint.name()))
                .selectedEndpoints(Endpoint::submit);
    }


}
