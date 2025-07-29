import base.components.core.Endpoint;
import user.scripts.requests.GeneralMakeStateful;
import base.components.core.TestRunner;
import base.components.core.Workspace;
import base.components.enums.Method;
import org.junit.jupiter.api.Test;
import user.scripts.responses.CheckStatusCode;
import user.utils.JwtTokenGenerator;
import user.utils.JwtTokenManager;
import user.workspaces.CreateTeamWorkspace;

import static user.workspaces.CreateTeamWorkspace.*;

public class RootFileImporterTest {

    CreateTeamWorkspace createTeamWorkspace = new CreateTeamWorkspace();
    TestRunner testRunner = new TestRunner();

    @Test
    public void testWrappedWorkspace() {

        testRunner.testName("My Test").withIterations(10).execute((context) -> {

            Workspace workspace = context.workspace(createTeamWorkspace.create());

            JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();
            Endpoint loginEndpoint = workspace.c$(PAYMENT_COLLECTION_NAME).e$(PC_ENDPOINT_LOGIN);
            JwtTokenManager jwtTokenManager = new JwtTokenManager();
            jwtTokenManager.setJwtTokenGenerator(
                    10,
                    jwtTokenGenerator,
                    loginEndpoint
            );

            workspace.c$(PAYMENT_COLLECTION_NAME)

                    .background((collection) -> {
                        collection.getEndpoints().forEach(endpoint -> {

                            if (!endpoint.equals(loginEndpoint)) {
                                endpoint.queueRequestTrigger("Generate new token after a number of uses", jwtTokenManager);
                            }
                        });
                    })


                    .given((collection) -> {
                        int iteration = context.getIteration();

                        collection
                                .set("preToken", jwtTokenGenerator.generateToken())
                                .set("canal", 10)
                                .set("numeroo", 20)
                                .set("tipo-ponto-venda", 30)
                                .set("numero-ponto-venda", 40)
                                .set("origem", 50)
                                .set("ca", "https://www.host.com")
                                .set("api-key", "PMAK-685828898ccc110001854e2f-f645a11dd1497a7b1397d1452e6aebf7ac")
                                .set("campanha", "a campanha...");
                    })




                    .when((c -> {
                        c.e$(PC_ENDPOINT_LOGIN).submit();
                        c.e$(PC_ENDPOINT_PAYBYCARD).getRequestScript(GeneralMakeStateful.class).exec()
                                .submit();
                    }))




                    .then((c) -> {
                        assert c.lastSubmittedEndpoint().getResponseScript(CheckStatusCode.class).isStatusCode(200);
                    });
        });

    }


    @Test
    public void testWrappedWorkspace2() {
        CreateTeamWorkspace createTeamWorkspace = new CreateTeamWorkspace();
        Workspace workspace = createTeamWorkspace.create();
        workspace
                .background((w) -> {
                    w.c$("My Collection").getEndpoint("Endpoint 1").setMethod(Method.POST);
                })

                .given("ddd", "collectionName", (collection) -> {
                    collection.getEndpoint("Endpoint 1").setUrl("http://xpto");
                })

                .and("bla", "collection2", (collection) -> {
                    collection.getEndpoints().get(0);
                })

                .given("dfdf", "dfdf", (collection) -> {


                })


                .then("Conclusion", "My Collection", (collection) -> {
                    assert
                            collection.e$("Endpoint 3").getResponseScript(CheckStatusCode.class).isStatusCode(200);
                });
    }
}
