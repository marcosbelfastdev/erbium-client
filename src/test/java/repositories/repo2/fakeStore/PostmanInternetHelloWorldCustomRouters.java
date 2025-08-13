package repositories.repo2.fakeStore;

import admin.common.factories.MasterFakeStoreFactory;
import br.com.erbium.core.*;
import org.junit.jupiter.api.Test;
import user.utils.JwtTokenGenerator;


import java.time.Duration;

public class PostmanInternetHelloWorldCustomRouters {

    @Test
    public void test() {

        OutputConfig config = new OutputConfig() // creates and sets WHAT is logged (all by default, then:)
                .set(TargetOutput.NONE, EItem.ENVIRONMENT_TABLE) // remove item from destinations
                .set(TargetOutput.CONSOLE, EItem.REQUEST_METHOD, EItem.REQUEST_URL); // and leave some items for console

        Routers routers = new Routers(config); // create router container
        routers.add(new DefaultConsoleRouter()); // add first report router (console in this case)

        // If you want to use the same routers elsewhere, wrap it into ErbiumConfigurator (optional)
        ErbiumConfigurator configurator = new ErbiumConfigurator();
        configurator.setRouters(routers);

        // Create the workspace
        Workspace workspace = new Workspace()
                .setRouters(routers); // set the routers container created above
        // Note: when the workspace is created and no routers are set explicitly,
        // a default router container and router (console) will be created automatically.

        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

        workspace
                // Create empty collection
                .addCollection(MasterFakeStoreFactory.FAKESTORE_API)

                // Import from Postman
                // You need to import postman collection json in 'resources' to Postman first
                // and customize UID and API KEY

                // This imports by hitting and downloading the postman collection from Postman servers
                // Note the Duration parameter. Remove it or set it to a few seconds to always download from Postman
                // else it will hit the cached json file in user profile/.erbium/ directory

                .importPostManCollection(MasterFakeStoreFactory.UID, MasterFakeStoreFactory.KEY, Duration.ofDays(365))

                // Select some endpoints for batch submission later (order is important)
                .e$(MasterFakeStoreFactory.LOGIN).select()
                .e$(MasterFakeStoreFactory.GET_PRODUCTS).select()
                .e$(MasterFakeStoreFactory.GET_CATEGORIES).select()

                // Set the collection environment variables required
                .set("{{access-token}}", jwtTokenGenerator.generateToken())
                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_")

                .e$(MasterFakeStoreFactory.LOGIN)
                // Force the variable token to get the value of token property after response
                .qrset("token", "$.token").backToCollection()

                // submit all selected endpoints in one go
                .selectedEndpoints(Endpoint::submit);
    }
}
