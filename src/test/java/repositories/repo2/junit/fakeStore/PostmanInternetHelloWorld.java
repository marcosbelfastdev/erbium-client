package repositories.repo2.junit.fakeStore;

import br.com.erbium.core.*;
import org.junit.jupiter.api.Test;
import user.utils.JwtTokenGenerator;

import java.time.Duration;

import static admin.common.factories.MasterFakeStoreFactory.*;

public class PostmanInternetHelloWorld {

    @Test
    public void test() {

        Workspace workspace = new Workspace();
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
}
