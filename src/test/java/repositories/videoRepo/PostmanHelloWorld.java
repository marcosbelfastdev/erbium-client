package repositories.videoRepo;

import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import br.com.erbium.core.enums.RequestType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static admin.common.factories.MasterFakeStoreFactory.FAKESTORE_POSTMAN_API_KEY;

public class PostmanHelloWorld {

    Workspace workspace;

    @BeforeEach
    public void setup() {
        workspace = new Workspace();
    }

    @Test
    public void test1() {
        workspace
                .addCollection("Fake Store")
                .importPostManCollection("10891144-bbb2afb7-123c-4e52-aeb2-432e40ac004c", FAKESTORE_POSTMAN_API_KEY)

                .e$("Login|Login").select()
                .e$("Products|Get Products").select()
                .e$("Categories|Get Categories").select()

                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_")

                .e$("Login|Login")
                .qrset("token", "$.token").backToCollection()

                .selectedEndpoints(Endpoint::submit);
    }
}
