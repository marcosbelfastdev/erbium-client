package admin.common.factories;

import admin.common.PostmanKey;
import admin.common.scripts.responses.CheckStatusCode;
import admin.fakeStore.scripts.responses.SchemaValidation;
import br.com.erbium.core.Workspace;
import repositories.common.fakeStore.FakeStoreSchemas;

import java.time.Duration;

public class MasterFakeStoreFactory {

    public static Workspace createWorkspace() {

        Workspace workspace = new Workspace();
        workspace
                // Create empty collection
                .addCollection(FAKESTORE_COLLECTION)

                // Import from Postman
                // You need to import postman collection json in 'resources' to Postman first
                // and customize UID and API KEY

                // This imports by hitting and downloading the postman collection from Postman servers
                // Note the Duration parameter. Remove it or set it to a few seconds to always download from Postman
                // else it will hit the cached json file in user profile/.erbium/ directory
                .importPostManCollection(FAKESTORE_COLLECTION_UID, FAKESTORE_POSTMAN_API_KEY, Duration.ofHours(12))


                // Add the general codes to all endpoints
                .getEndpoints().forEach(endpoint -> {
                    endpoint.addResponseScript("Check status code", new CheckStatusCode(endpoint));
                    String endpointName = endpoint.name();
                    endpoint.addResponseScript("Check schema", new SchemaValidation().setSchema(FakeStoreSchemas.get(endpoint), endpoint));
                });

        return workspace;
    }

    // Collections and endpoint constants
    public static final String FAKESTORE_COLLECTION = "Fake Store API Collection";
    public static final String FAKESTORE_COLLECTION_UID = "10891144-bbb2afb7-123c-4e52-aeb2-432e40ac004c";
    public static final String FAKESTORE_POSTMAN_API_KEY = PostmanKey.demoKey();
    public static final String FAKESTORE_LOGIN = "Login|Login";
    public static final String FAKESTORE_PRODUCTS = "Products|";
    public static final String FAKESTORE_DELETE_PRODUCT = FAKESTORE_PRODUCTS + "Delete a single product";
    public static final String FAKESTORE_UPDATE_PRODUCT = FAKESTORE_PRODUCTS + "Update product";
    public static final String FAKESTORE_ADD_PRODUCT = FAKESTORE_PRODUCTS + "Add a product";
    public static final String FAKESTORE_GET_PRODUCTS_BY_CATEGORY = FAKESTORE_PRODUCTS + "Get products by category";
    public static final String FAKESTORE_GET_CATEGORIES = FAKESTORE_PRODUCTS + "Get categories";
    public static final String FAKESTORE_GET_SINGLE_PRODUCT = FAKESTORE_PRODUCTS + "Get single product";
    public static final String FAKESTORE_GET_PRODUCTS = FAKESTORE_PRODUCTS + "Get products";
    public static final String FAKESTORE_USERS = "Users|";
    public static final String FAKESTORE_ADD_USER = FAKESTORE_USERS + "Add user";
    public static final String FAKESTORE_GET_SINGLE_USER = FAKESTORE_USERS + "Get single user";
    public static final String FAKESTORE_GET_USERS = FAKESTORE_USERS + "Get users";

    // Script names
    public static final String CHECK_STATUS_CODE = "Check status code";
    public static final String CHECK_SCHEMA = "Check schema";

}
