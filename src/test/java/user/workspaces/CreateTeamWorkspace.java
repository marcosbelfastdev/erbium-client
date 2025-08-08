package user.workspaces;

import br.com.erbium.core.Workspace;
import user.scripts.requests.GeneralMakeStateful;
import user.scripts.responses.CheckStatusCode;

public class CreateTeamWorkspace {


    public static final String PAYMENT_COLLECTION_ID = "10891144-aa7f3926-2762-4a3d-ab3f-115c1a2f4c27";
    public static final String PAYMENT_COLLECTION_APIKEY = "PMAK-685828898ccc110001854e2f-f645a11dd1497a7b1397d1452e6aebf7ac";
    public static final String PAYMENT_COLLECTION_NAME = "My Collection";

    public static final String PC_ENDPOINT_LOGIN = "Login Endpoint";
    public static final String PC_ENDPOINT_PAYBYCARD = "Pay By Card Endpoint";

    Workspace workspace = new Workspace();

    public Workspace create() {



        workspace = new Workspace();
//        workspace
//                .addCollection(PAYMENT_COLLECTION_NAME)
//                .importPostManCollection(
//                        PAYMENT_COLLECTION_ID,
//                        PAYMENT_COLLECTION_APIKEY)
//                .getEndpoints().forEach(endpoint -> {
//                    endpoint.addRequestScript("General Make Stateful", GeneralMakeStateful.class)
//                            .addResponseScript("Check Status Code", CheckStatusCode.class);
//                });



























































        return workspace;
    }

    public Workspace get() {
        return workspace;
    }
}
