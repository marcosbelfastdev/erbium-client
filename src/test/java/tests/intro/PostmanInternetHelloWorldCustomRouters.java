package tests.intro;

import br.com.erbium.core.*;
import org.junit.jupiter.api.Test;
import user.utils.JwtTokenGenerator;
import user.workspaces.collections.FakeStoreApiCollection;

import java.time.Duration;

import static user.workspaces.collections.FakeStoreApiCollection.*;

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
                .addCollection(FakeStoreApiCollection.FAKESTORE_API)
                // import from Postman
                .importPostManCollection(FakeStoreApiCollection.UID, FakeStoreApiCollection.KEY, Duration.ofDays(365))

                // Select some endpoints for batch submission later
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
