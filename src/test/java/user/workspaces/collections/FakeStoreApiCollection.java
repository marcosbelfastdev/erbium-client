package user.workspaces.collections;

import user.workspaces.PostmanKey;

public interface FakeStoreApiCollection {

    String FAKESTORE_API = "Fake Store API Collection";
    String UID = "10891144-bbb2afb7-123c-4e52-aeb2-432e40ac004c";
    String KEY = PostmanKey.demoKey();

    String LOGIN = "Login|Login";
    String PRODUCTS = "Products|";
    String GET_PRODUCTS = PRODUCTS + "Get products";
    String GET_SINGLE_PRODUCT = PRODUCTS + "Get single product";
    String GET_CATEGORIES = PRODUCTS + "Get categories";
    String GET_PRODUCTS_BY_CATEGORY = PRODUCTS + "Get products by category";
    String ADD_PRODUCT = PRODUCTS + "Add a product";
    String UPDATE_PRODUCT = PRODUCTS + "Update product";
    String DELETE_PRODUCT = PRODUCTS + "Delete a single product";
    String USERS = "Users|";
    String GET_USERS = USERS + "Get users";
    String GET_SINGLE_USER = USERS + "Get single user";
    String ADD_USER = USERS + "Add user";



}
