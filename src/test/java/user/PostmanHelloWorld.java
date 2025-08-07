package user;

import base.components.core.Endpoint;
import base.components.core.Workspace;
import org.junit.jupiter.api.Test;
import user.utils.JwtTokenGenerator;
import user.workspaces.collections.FakeStoreApiCollection;

import static user.workspaces.collections.FakeStoreApiCollection.*;

public class PostmanHelloWorld {

    @Test
    public void test() {

        Workspace workspace = new Workspace();
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace
                .addCollection(FakeStoreApiCollection.FAKESTORE_API)
                .importPostManCollection(FakeStoreApiCollection.UID, FakeStoreApiCollection.KEY)

                .e$(GET_USERS).select()
                .e$(LOGIN).qrset("token", "$.token").select()
                .e$(GET_PRODUCTS).select()
                .e$(GET_CATEGORIES).select()

                .set("{{jwtToken}}", jwtTokenGenerator.generateToken())
                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_")
                .set("bearer", "Bearer {{token}}")

                .e$(LOGIN).qrset("token", "$.token").backToCollection()
                .selectedEndpoints(Endpoint::submit);


    }
}
