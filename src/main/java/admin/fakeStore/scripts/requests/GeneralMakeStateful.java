package admin.fakeStore.scripts.requests;


import br.com.erbium.core.Endpoint;
import br.com.erbium.core.base.scripts.RequestScript;

public class GeneralMakeStateful extends RequestScript {



    @Override
    public Endpoint exec() {
        System.out.println("I've made this request a stateful request");
        return requestManager().parentEndpoint();
    }

    @Override
    public void run() {

    }
}
