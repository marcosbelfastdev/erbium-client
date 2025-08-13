package admin.common.factories;

import admin.common.PostmanKey;
import admin.common.scripts.responses.CheckStatusCode;
import br.com.erbium.core.Workspace;

public class MasterFakeStoreFactory {

    public static Workspace createWorkspace() {

        Workspace workspace = new Workspace();
        workspace
                // Create empty collection
                .addCollection(FAKESTORE_API)

                // Import from Postman
                // You need to import postman collection json in 'resources' to Postman first
                // and customize UID and API KEY

                // This imports by hitting and downloading the postman collection from Postman servers
                // Note the Duration parameter. Remove it or set it to a few seconds to always download from Postman
                // else it will hit the cached json file in user profile/.erbium/ directory
                .importPostManCollection(UID, KEY)


                // Add the general codes to all endpoints
                .getEndpoints().forEach(endpoint -> {
                    endpoint.addResponseScript("Check status code", CheckStatusCode.class);
                });


        return workspace;
    }

    public static final String FAKESTORE_API = "Fake Store API Collection";
    public static final String UID = "10891144-bbb2afb7-123c-4e52-aeb2-432e40ac004c";
    public static final String KEY = PostmanKey.demoKey();
    public static final String LOGIN = "Login|Login";
    public static final String PRODUCTS = "Products|";
    public static final String DELETE_PRODUCT = PRODUCTS + "Delete a single product";
    public static final String UPDATE_PRODUCT = PRODUCTS + "Update product";
    public static final String ADD_PRODUCT = PRODUCTS + "Add a product";
    public static final String GET_PRODUCTS_BY_CATEGORY = PRODUCTS + "Get products by category";
    public static final String GET_CATEGORIES = PRODUCTS + "Get categories";
    public static final String GET_SINGLE_PRODUCT = PRODUCTS + "Get single product";
    public static final String GET_PRODUCTS = PRODUCTS + "Get products";
    public static final String USERS = "Users|";
    public static final String ADD_USER = USERS + "Add user";
    public static final String GET_SINGLE_USER = USERS + "Get single user";
    public static final String GET_USERS = USERS + "Get users";

}
