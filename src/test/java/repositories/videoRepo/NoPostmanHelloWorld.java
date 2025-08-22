package repositories.videoRepo;

import br.com.erbium.core.Endpoint;
import br.com.erbium.core.Workspace;
import br.com.erbium.core.enums.Method;
import br.com.erbium.core.enums.RequestType;
import customization.utils.JwtTokenGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static admin.common.factories.MasterFakeStoreFactory.*;

public class NoPostmanHelloWorld {

    Workspace workspace;

    @BeforeEach
    public void setup() {
        workspace = new Workspace();
        workspace
                .addCollection("My Fake Store Without Postman Collection")


                .addEndpoint("Login", RequestType.JSON)
                .setHost("{{host}}")
                .setUrl("{{host}}/auth/login")
                .setMethod(Method.POST)
                .addHeader("Authorization", "{{access-token}}")
                .getJsonRequest()
                .setBody("""
                        {
                            username:"{{userName}}",
                            password:"{{userPassword}}"
                        
                        """).backToEndpoint()
                .qrset("{{token}}", "$.token").backToCollection()


                .addEndpoint("Get products", RequestType.JSON)
                .setHost("{{host}}")
                .setUrl("{{host}}/products")
                .setMethod(Method.GET);
    }

    @Test
    public void test() {

        workspace
                .c$("My Fake Store Without Postman Collection")

                .set("host", "https://fakestoreapi.com")
                .set("userName", "mor_2314")
                .set("userPassword", "83r5^_")
                .set("access-token", "isdoivsodi3489539845")

                .e$("Login").select()
                .e$("Get products").select()

                .selectedEndpoints(Endpoint::submit);
    }

}
