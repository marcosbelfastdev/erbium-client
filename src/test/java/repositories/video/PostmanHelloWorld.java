package repositories.video;

import admin.common.factories.MasterFakeStoreFactory;
import admin.common.scripts.responses.CheckStatusCode;
import admin.fakeStore.scripts.responses.SchemaValidation;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.common.fakeStore.FakeStoreSchemas;

import static admin.common.factories.MasterFakeStoreFactory.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostmanHelloWorld {

    Workspace workspace;

    @BeforeEach
    public void setup() {

        workspace = new Workspace();
        workspace
                .addCollection(FAKESTORE_COLLECTION)
                .importPostManCollection(FAKESTORE_COLLECTION_UID, FAKESTORE_POSTMAN_API_KEY)

                .e$(FAKESTORE_LOGIN).qrset("token", "$.token").select()
                .e$(FAKESTORE_GET_CATEGORIES).qrset("productCategory", "$.[-1]").select()
                .e$(FAKESTORE_GET_PRODUCTS_BY_CATEGORY).qrset("productId", "$.[-1]id").select()
                .e$(FAKESTORE_GET_SINGLE_PRODUCT).select()

                .selectedEndpoints(ep -> {
                    String schema = FakeStoreSchemas.get(ep);
                    ep
                            .addResponseScript("Check Status Code", new CheckStatusCode(ep))
                            .addResponseScript("Check schema", new SchemaValidation().setSchema(schema, ep));
                });
    }

    @Test
    public void test() {

        workspace
                .c$(FAKESTORE_COLLECTION)

                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_")

                .selectedEndpoints(ep -> {
                    ep.submit();
                    assertTrue(ep.getResponseScript(CheckStatusCode.class).isStatusCodeAnyOf(200, 201), "Unexpected status code");
                    assertTrue(ep. getResponseScript(SchemaValidation.class).isValidSchema(), "Invalid  schema");
                });
    }
}
