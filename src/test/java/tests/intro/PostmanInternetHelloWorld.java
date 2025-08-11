package tests.intro;

import br.com.erbium.core.*;
import org.junit.jupiter.api.Test;
import user.utils.JwtTokenGenerator;
import user.workspaces.collections.FakeStoreApiCollection;

import java.time.Duration;

import static user.workspaces.collections.FakeStoreApiCollection.*;

public class PostmanInternetHelloWorld {

    @Test
    public void test() {

        Workspace workspace = new Workspace();
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace
                // Create empty collection
                .addCollection(FakeStoreApiCollection.FAKESTORE_API)

                // Import from Postman
                // You need to import postman collection json in 'resources' to Postman first
                // and customize UID and API KEY

                // This imports by hitting and downloading the postman collection from Postman servers
                // Note the Duration parameter. Remove it or set it to a few seconds to always download from Postman
                // else it will hit the cached json file in user profile/.erbium/ directory
                .importPostManCollection(FakeStoreApiCollection.UID, FakeStoreApiCollection.KEY, Duration.ofDays(365))

                // Select some endpoints for batch submission later (order is important)
                .e$(LOGIN).select()
                .e$(GET_PRODUCTS).select()
                .e$(GET_CATEGORIES).select()

                // Set the collection environment variables required
                .set("{{access-token}}", jwtTokenGenerator.generateToken())
                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_")

                .e$(LOGIN)
                // Force the variable token to get the value of token property after response
                .qrset("token", "$.token").backToCollection()

                // submit all selected endpoints in one go
                .selectedEndpoints(Endpoint::submit);


    }
}
