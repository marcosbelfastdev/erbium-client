package repositories.video;

import admin.common.scripts.responses.CheckStatusCode;
import admin.fakeStore.scripts.responses.SchemaValidation;
import br.com.erbium.core.Endpoint;
import br.com.erbium.core.LogItem;
import br.com.erbium.core.LogType;
import br.com.erbium.core.Workspace;
import customization.utils.JwtTokenGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.common.fakeStore.FakeStoreSchemas;

import java.time.Duration;

import static admin.common.factories.MasterFakeStoreFactory.*;
import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_GET_CATEGORIES;
import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_GET_PRODUCTS;
import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_LOGIN;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostmanHelloWorld {

    Workspace workspace;


    @BeforeEach
    public void setup() {
        workspace = new Workspace()

                .addCollection(FAKESTORE_COLLECTION)
                .importPostManCollection(FAKESTORE_COLLECTION_UID, FAKESTORE_POSTMAN_API_KEY, Duration.ofDays(365))

                .e$(FAKESTORE_LOGIN).qrset("token", "$.token").backToCollection()
                .e$(FAKESTORE_GET_CATEGORIES).qrset("productCategory", "$.[-1]").backToCollection()
                .e$(FAKESTORE_GET_PRODUCTS_BY_CATEGORY).qrset("productId", "$[-1]id").backToCollection()
                .e$(FAKESTORE_GET_SINGLE_PRODUCT).backToCollection()

                .selectedEndpoints(ep -> {
                    String schema = FakeStoreSchemas.get(ep);
                    ep.addResponseScript("Check Status Code", new CheckStatusCode(ep))
                      .addResponseScript("Check schema", new SchemaValidation().setSchema(schema, ep));
                })
                .workspaceContext();
    }

    @Test
    public void test() {

        workspace
                .c$(FAKESTORE_COLLECTION)

                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_")

                .e$(FAKESTORE_LOGIN).select()
                .e$(FAKESTORE_GET_CATEGORIES).select()
                .e$(FAKESTORE_GET_PRODUCTS_BY_CATEGORY).select()
                .e$(FAKESTORE_GET_SINGLE_PRODUCT).select()

                .selectedEndpoints(ep -> {
                    ep.submit();
                    assertTrue(ep.getResponseScript(CheckStatusCode.class).isStatusCodeAnyOf(200, 201), "Unexpected status code");
                    assertTrue(ep.getResponseScript(SchemaValidation.class).isValidSchema(), "Invalid schema");
                });
    }
}
