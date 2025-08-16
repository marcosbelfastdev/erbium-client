package repositories.repo2.junit.fakeStore;

import admin.common.factories.MasterFakeStoreFactory;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import org.junit.jupiter.api.Test;
import customization.utils.JwtTokenGenerator;


public class PostmanJsonHelloWorld {

    @Test
    public void test() {

        /**
         *
         * WE NEED TO ADD FEATURE TO IMPORT FROM A JSON FILE INSTEAD OF POSTMAN
         *
         */

        Workspace workspace = new Workspace();
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace
                // Create empty collection
                .addCollection(MasterFakeStoreFactory.FAKESTORE_COLLECTION)
                // import from Postman
                    //.importPostManCollection("./src/test/resources/good_10891144-bbb2afb7-123c-4e52-aeb2-432e40ac004c")

                // Select some endpoints for batch submission later
                .e$(MasterFakeStoreFactory.FAKESTORE_LOGIN).select()
                .e$(MasterFakeStoreFactory.FAKESTORE_GET_PRODUCTS).select()
                .e$(MasterFakeStoreFactory.FAKESTORE_GET_CATEGORIES).select()

                // Select some endpoints for batch submission later (order is important)
                .set("{{access-token}}", jwtTokenGenerator.generateToken())
                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_")

                .e$(MasterFakeStoreFactory.FAKESTORE_LOGIN)
                // Force the variable token to get the value of token property after response
                .qrset("token", "$.token").backToCollection()

                // submit all selected endpoints in one go
                .selectedEndpoints(Endpoint::submit);


    }
}
