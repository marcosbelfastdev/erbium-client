package repositories.common.fakeStore;

import admin.common.factories.MasterFakeStoreFactory;

public class FakeStoreSchemas {

    public static String get(String endpointName) {
        switch (endpointName) {
            case MasterFakeStoreFactory.FAKESTORE_LOGIN:
                return """
            {
                      "$schema": "https://json-schema.org/draft/2020-12/schema",
                      "type": "object",
                      "properties": {
                        "token": { "type": "string" }
                      },
                      "required": ["token"],
                      "additionalProperties": false
                    }
            """;
            case MasterFakeStoreFactory.FAKESTORE_GET_CATEGORIES:
                return """
                        {
                          "$schema": "https://json-schema.org/draft/2020-12/schema",
                          "type": "array",
                          "items": {
                            "type": "string",
                            "enum": [
                              "electronics",
                              "jewelery",
                              "men's clothing",
                              "women's clothing"
                            ]
                          },
                          "minItems": 1,
                          "uniqueItems": true
                        }
                        """;
            default:
                return "";
        }
    }

}
